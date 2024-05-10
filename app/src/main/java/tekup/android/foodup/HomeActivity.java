package tekup.android.foodup;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
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

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = 1;
    private final int pageNumber = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = (RecyclerView)  findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        categoriesAdapter = new CategoriesAdapter(getApplicationContext());
        recyclerView.setAdapter(categoriesAdapter);

        fetchCategories(currentPage);
        setRecyclerViewScrollListener();
    }

    private void fetchCategories(int page) {
        String jwtToken = JwtManager.getJwtToken(this);
        CategoriesAPICall categoriesAPICall = ApiClient.getApiService(CategoriesAPICall.class, jwtToken);
        Call<CategoriesResponse> call = categoriesAPICall.getCategories(currentPage,pageNumber);

        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.isSuccessful()) {
                    CategoriesResponse categoryResponse = response.body();
                    if (categoryResponse != null && categoryResponse.getCategories() != null) {
                        List<Category> categories = categoryResponse.getCategories();
                        if (categories.isEmpty()) {
                            isLastPage = true;
                        } else {
                            System.out.println("sar call api");
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
                        fetchCategories(currentPage);
                    }
                }
            }
        });
    }
}