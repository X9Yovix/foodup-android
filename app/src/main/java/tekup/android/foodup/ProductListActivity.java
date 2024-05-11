package tekup.android.foodup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tekup.android.foodup.adapters.ProductsAdapter;
import tekup.android.foodup.api.ApiClient;
import tekup.android.foodup.api.interfaces.ProductsAPICall;
import tekup.android.foodup.api.model.Product;
import tekup.android.foodup.api.network.ProductsResponse;

public class ProductListActivity extends AppCompatActivity {
    private TextView categoryTitle;
    private RecyclerView recyclerView;
    private ProductsAdapter productsAdapter;
    private BottomNavigationView bottomNavigationView;

    private int categoryId;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = 1;
    private final int pageNumber = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_categories);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_categories) {
                startActivity(new Intent(ProductListActivity.this, HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_left_bottom_menu, R.anim.slide_out_right_bottom_menu);
                finish();
                return true;
            } else if (id == R.id.menu_deals) {
                return true;
            } else if (id == R.id.menu_orders) {
                return true;
            } else if (id == R.id.menu_profile) {
                startActivity(new Intent(ProductListActivity.this, SettingsActivity.class));
                overridePendingTransition(R.anim.slide_in_right_bottom_menu, R.anim.slide_out_left_bottom_menu);
                finish();
                return true;
            } else {
                return false;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        categoryTitle = (TextView) findViewById(R.id.categoryTitle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productsAdapter = new ProductsAdapter(this);
        recyclerView.setAdapter(productsAdapter);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("categoryId")) {
            categoryId = intent.getIntExtra("categoryId", -1);
            if (categoryId != -1) {
                categoryTitle.setText(intent.getStringExtra("categoryName"));
                fetchProducts(categoryId, currentPage);
                setRecyclerViewScrollListener();
            }
        } else {
            Toast.makeText(this, "Category ID not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchProducts(int categoryId, int page) {
        isLoading = true;
        ProductsAPICall productsAPICall = ApiClient.getApiService(ProductsAPICall.class, "");
        Call<ProductsResponse> call = productsAPICall.getProductsByCategory(categoryId, page, pageNumber);

        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                isLoading = false;
                if (response.isSuccessful()) {
                    ProductsResponse productsResponse = response.body();
                    if (productsResponse != null && productsResponse.getProducts() != null) {
                        List<Product> products = productsResponse.getProducts();
                        if (products.isEmpty()) {
                            isLastPage = true;
                        } else {
                            productsAdapter.addAll(products);
                            currentPage++;
                        }
                    } else {
                        Toast.makeText(ProductListActivity.this, "No products found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProductListActivity.this, "Failed to fetch products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                isLoading = false;
                Toast.makeText(ProductListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= pageNumber) {
                        fetchProducts(categoryId, currentPage);
                    }
                }
            }
        });
    }

}