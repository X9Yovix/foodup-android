package tekup.android.foodup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tekup.android.foodup.adapters.MyOrdersAdapter;
import tekup.android.foodup.api.ApiClient;
import tekup.android.foodup.api.interfaces.CategoriesAPICall;
import tekup.android.foodup.api.interfaces.OrdersAPICall;
import tekup.android.foodup.api.interfaces.ProductsAPICall;
import tekup.android.foodup.api.model.OrderItem;
import tekup.android.foodup.api.model.Product;
import tekup.android.foodup.api.network.OrderCreateRequest;
import tekup.android.foodup.api.network.OrderCreateResponse;
import tekup.android.foodup.api.network.ProductsResponse;
import tekup.android.foodup.api.utility.JwtManager;
import tekup.android.foodup.api.utility.ProductManager;

public class MyOrdersActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<Product> ordersAdapter;
    private BottomNavigationView bottomNavigationView;
    private Button placeOrderButton;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        listView = (ListView) findViewById(R.id.listView);
        placeOrderButton = (Button) findViewById(R.id.placeOrderButton);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setSelectedItemId(R.id.menu_orders);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_categories) {
                startActivity(new Intent(MyOrdersActivity.this, HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_left_bottom_menu, R.anim.slide_out_right_bottom_menu);
                finish();
                return true;
            } else if (id == R.id.menu_deals) {
                return true;
            } else if (id == R.id.menu_orders) {
                return true;
            } else if (id == R.id.menu_profile) {
                startActivity(new Intent(MyOrdersActivity.this, SettingsActivity.class));
                overridePendingTransition(R.anim.slide_in_right_bottom_menu, R.anim.slide_out_left_bottom_menu);
                finish();
                return true;
            } else {
                return false;
            }
        });
        List<Integer> productIds = getSelectedProductIds();
        if (productIds.isEmpty()){
            placeOrderButton.setVisibility(View.INVISIBLE);
        }
        fetchProductsById(productIds);
        placeOrderButton.setOnClickListener(v -> {
            isLoading = true;
            List<Product> productList = new ArrayList<>();
            for (int i = 0; i < ordersAdapter.getCount(); i++) {
                productList.add(ordersAdapter.getItem(i));
            }

            OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
            List<OrderItem> orderItems = new ArrayList<>();
            for (Product product : productList) {
                OrderItem orderItem = new OrderItem(product.getId(),product.getQuantity());
                orderItems.add(orderItem);
            }
            orderCreateRequest.setOrderItems(orderItems);

            String jwtToken = JwtManager.getJwtToken(this);
            OrdersAPICall ordersAPICall = ApiClient.getApiService(OrdersAPICall.class, jwtToken);
            Call<OrderCreateResponse> call = ordersAPICall.saveOrder(orderCreateRequest);
            call.enqueue(new Callback<OrderCreateResponse>() {
                @Override
                public void onResponse(Call<OrderCreateResponse> call, Response<OrderCreateResponse> response) {
                    isLoading = false;
                    if (response.isSuccessful()) {
                        OrderCreateResponse orderCreateResponse = response.body();
                        if (orderCreateResponse != null) {
                            String message = orderCreateResponse.getMessage();
                            Toast.makeText(MyOrdersActivity.this, message, Toast.LENGTH_SHORT).show();
                            ProductManager.clearProductIds(getApplicationContext());
                            List<Product> emptyList = new ArrayList<>();
                            ordersAdapter.clear();
                            ordersAdapter.addAll(emptyList);
                            ordersAdapter.notifyDataSetChanged();
                            placeOrderButton.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        Toast.makeText(MyOrdersActivity.this, "Failed to create order", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OrderCreateResponse> call, Throwable t) {
                    isLoading = false;
                    Toast.makeText(MyOrdersActivity.this, "Failed to create order: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private List<Integer> getSelectedProductIds() {
        String productIdsString = ProductManager.getProductIds(this);
        List<Integer> productIds = new ArrayList<>();
        if (!productIdsString.isEmpty()) {
            String[] idsArray = productIdsString.split(",");
            for (String id : idsArray) {
                productIds.add(Integer.parseInt(id));
            }
        }
        return productIds;
    }

    private void fetchProductsById(List<Integer> productIds) {
        isLoading = true;
        String jwtToken = JwtManager.getJwtToken(this);
        ProductsAPICall productsAPICall = ApiClient.getApiService(ProductsAPICall.class, jwtToken);

        System.out.println(productIds);
        Call<ProductsResponse> call = productsAPICall.getProductsByIds(productIds);

        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                isLoading = false;
                if (response.isSuccessful()) {
                    ProductsResponse productsResponse = response.body();
                    if (productsResponse != null && productsResponse.getProducts() != null) {
                        List<Product> products = productsResponse.getProducts();
                        ordersAdapter = new MyOrdersAdapter(MyOrdersActivity.this, R.layout.item_list_my_orders, products);
                        listView.setAdapter(ordersAdapter);
                    } else {
                        Toast.makeText(MyOrdersActivity.this, "No products found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    System.out.println("Error fetching products: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable throwable) {
                isLoading = false;
                System.out.println("Error fetching products: " + throwable.getMessage());
            }
        });
    }
}
