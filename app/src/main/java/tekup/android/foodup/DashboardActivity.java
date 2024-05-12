package tekup.android.foodup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tekup.android.foodup.api.ApiClient;
import tekup.android.foodup.api.interfaces.AuthAPICall;
import tekup.android.foodup.api.interfaces.CategoriesAPICall;
import tekup.android.foodup.api.interfaces.OrdersAPICall;
import tekup.android.foodup.api.interfaces.ProductsAPICall;
import tekup.android.foodup.api.network.CountResponse;
import tekup.android.foodup.api.utility.JwtManager;

public class DashboardActivity extends AppCompatActivity {
    private TextView productsTextView;
    private TextView categoriesTextView;
    private TextView ordersTextView;
    private TextView usersTextView;
    private Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        productsTextView = (TextView) findViewById(R.id.productsTextView);
        categoriesTextView = (TextView) findViewById(R.id.categoriesTextView);
        ordersTextView = (TextView) findViewById(R.id.ordersTextView);
        usersTextView = (TextView) findViewById(R.id.usersTextView);
        logoutButton = (Button) findViewById(R.id.logoutButton);

        fetchProductsCount();
        fetchCategoriesCount();
        fetchOrdersCount();
        fetchUsersCount();

        logoutButton.setOnClickListener(v -> {
            JwtManager.logout(getApplicationContext());
            startActivity(new Intent(DashboardActivity.this, SignInActivity.class));
            finish();
        });
    }

    private void fetchProductsCount() {
        String jwtToken = JwtManager.getJwtToken(this);
        ProductsAPICall productService = ApiClient.getApiService(ProductsAPICall.class, jwtToken);
        Call<CountResponse> call = productService.countProducts();
        call.enqueue(new Callback<CountResponse>() {
            @Override
            public void onResponse(Call<CountResponse> call, Response<CountResponse> response) {
                if (response.isSuccessful()) {
                    CountResponse productsCount = response.body();
                    productsTextView.setText(String.valueOf(productsCount.getCount())+ " Products");
                } else {
                    productsTextView.setText("N/A");
                }
            }

            @Override
            public void onFailure(Call<CountResponse> call, Throwable t) {
                productsTextView.setText("Error");
            }
        });
    }

    private void fetchOrdersCount() {
        String jwtToken = JwtManager.getJwtToken(this);
        OrdersAPICall orderService = ApiClient.getApiService(OrdersAPICall.class, jwtToken);
        Call<CountResponse> call = orderService.countOrders();
        call.enqueue(new Callback<CountResponse>() {
            @Override
            public void onResponse(Call<CountResponse> call, Response<CountResponse> response) {
                if (response.isSuccessful()) {
                    CountResponse ordersCount = response.body();
                    ordersTextView.setText(String.valueOf(ordersCount.getCount())+ " Orders");
                } else {
                    ordersTextView.setText("N/A");
                }
            }

            @Override
            public void onFailure(Call<CountResponse> call, Throwable t) {
                ordersTextView.setText("Error");
            }
        });
    }

    private void fetchCategoriesCount() {
        String jwtToken = JwtManager.getJwtToken(this);
        CategoriesAPICall categoryService = ApiClient.getApiService(CategoriesAPICall.class, jwtToken);
        Call<CountResponse> call = categoryService.countCategories();
        call.enqueue(new Callback<CountResponse>() {
            @Override
            public void onResponse(Call<CountResponse> call, Response<CountResponse> response) {
                if (response.isSuccessful()) {
                    CountResponse categoriesCount = response.body();
                    categoriesTextView.setText(String.valueOf(categoriesCount.getCount()) + " Categories");
                } else {
                    categoriesTextView.setText("N/A");
                }
            }

            @Override
            public void onFailure(Call<CountResponse> call, Throwable t) {
                categoriesTextView.setText("Error");
            }
        });
    }

    private void fetchUsersCount() {
        String jwtToken = JwtManager.getJwtToken(this);
        AuthAPICall userService = ApiClient.getApiService(AuthAPICall.class, jwtToken);
        Call<CountResponse> call = userService.countUsers();
        call.enqueue(new Callback<CountResponse>() {
            @Override
            public void onResponse(Call<CountResponse> call, Response<CountResponse> response) {
                if (response.isSuccessful()) {
                    CountResponse usersCount = response.body();
                    usersTextView.setText(String.valueOf(usersCount.getCount()) + " Users");
                } else {
                    usersTextView.setText("N/A");
                }
            }

            @Override
            public void onFailure(Call<CountResponse> call, Throwable t) {
                usersTextView.setText("Error");
            }
        });
    }
}