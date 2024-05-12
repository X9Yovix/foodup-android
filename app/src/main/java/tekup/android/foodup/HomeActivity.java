package tekup.android.foodup;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tekup.android.foodup.adapters.CategoriesAdapter;
import tekup.android.foodup.api.ApiClient;
import tekup.android.foodup.api.interfaces.CategoriesAPICall;
import tekup.android.foodup.api.model.Category;
import tekup.android.foodup.api.network.CategoriesResponse;
import tekup.android.foodup.api.utility.JwtManager;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategoriesAdapter categoriesAdapter;
    private BottomNavigationView bottomNavigationView;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = 1;
    private final int pageNumber = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_categories);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_categories) {
                return true;
            } else if (id == R.id.menu_deals) {
                return true;
            } else if (id == R.id.menu_orders) {
                startActivity(new Intent(HomeActivity.this, MyOrdersActivity.class));
                overridePendingTransition(R.anim.slide_in_right_bottom_menu, R.anim.slide_out_left_bottom_menu);
                finish();
                return true;
            } else if (id == R.id.menu_profile) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                overridePendingTransition(R.anim.slide_in_right_bottom_menu, R.anim.slide_out_left_bottom_menu);
                finish();
                return true;
            } else {
                return false;
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        categoriesAdapter = new CategoriesAdapter(getApplicationContext());
        recyclerView.setAdapter(categoriesAdapter);
        fetchCategories(currentPage);
        setRecyclerViewScrollListener();
    }

    private void fetchCategories(int page) {
        isLoading = true;
        String jwtToken = JwtManager.getJwtToken(this);
        CategoriesAPICall categoriesAPICall = ApiClient.getApiService(CategoriesAPICall.class, jwtToken);
        Call<CategoriesResponse> call = categoriesAPICall.getCategories(currentPage, pageNumber);

        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                isLoading = false;
                if (response.isSuccessful()) {
                    CategoriesResponse categoryResponse = response.body();
                    if (categoryResponse != null && categoryResponse.getCategories() != null) {
                        List<Category> categories = categoryResponse.getCategories();
                        if (categories.isEmpty()) {
                            isLastPage = true;
                        } else {
                            categoriesAdapter.addAll(categories);
                            currentPage++;
                        }
                    } else {
                        Toast.makeText(HomeActivity.this, "No categories found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    System.out.println("Error fetching categories: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable throwable) {
                isLoading = false;
                System.out.println("Error fetching categories: " + throwable.getMessage());
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
                            && visibleItemCount > 0) {
                        isLoading = true;
                        fetchCategories(currentPage);
                    }
                }
            }
        });
    }
}