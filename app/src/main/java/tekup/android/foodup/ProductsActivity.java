package tekup.android.foodup;

import android.os.Bundle;
import android.widget.MultiAutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import tekup.android.foodup.api.ApiClient;
import tekup.android.foodup.api.interfaces.AuthAPICall;
import tekup.android.foodup.api.interfaces.CategoriesAPICall;
import tekup.android.foodup.api.network.CategoriesResponse;
import tekup.android.foodup.api.network.LoginResponse;

public class ProductsActivity extends AppCompatActivity {
    private MultiAutoCompleteTextView autoCompleteCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        autoCompleteCategories = (MultiAutoCompleteTextView) findViewById(R.id.AutoCompleteCategories);
        CategoriesAPICall categoriesAPICall = ApiClient.getApiService(CategoriesAPICall.class,"");
        Call<CategoriesResponse> call = categoriesAPICall.getCategories();
    }
}