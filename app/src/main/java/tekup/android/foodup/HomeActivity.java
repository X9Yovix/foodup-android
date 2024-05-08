package tekup.android.foodup;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tekup.android.foodup.api.ApiClient;
import tekup.android.foodup.api.interfaces.CategoriesAPICall;
import tekup.android.foodup.api.model.Category;
import tekup.android.foodup.api.network.CategoriesResponse;
import tekup.android.foodup.api.utility.JwtManager;

public class HomeActivity extends AppCompatActivity {
    private MultiAutoCompleteTextView multiAutoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        multiAutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView);

        fetchCategories();
    }

    private void fetchCategories() {
        String jwtToken = JwtManager.getJwtToken(this);
        CategoriesAPICall categoriesAPICall = ApiClient.getApiService(CategoriesAPICall.class, jwtToken);
        Call<CategoriesResponse> call = categoriesAPICall.getCategories();
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.isSuccessful()) {
                    CategoriesResponse categoriesResponse = response.body();
                    if (categoriesResponse != null) {
                        List<Category> categories = categoriesResponse.getCategories();
                        if (categories != null && !categories.isEmpty()) {
                            populateMultiAutoCompleteTextView(categories);
                        } else {
                            System.out.println("empty or null categories list");
                        }
                    } else {
                        System.out.println(response);
                    }
                } else {
                    System.out.println(response);
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable throwable) {
                System.out.println("throwable"+throwable);
            }
        });
    }


    private void populateMultiAutoCompleteTextView(List<Category> categories) {
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categoryNames);
        multiAutoCompleteTextView.setAdapter(adapter);
        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}