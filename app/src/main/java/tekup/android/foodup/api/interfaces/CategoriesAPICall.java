package tekup.android.foodup.api.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import tekup.android.foodup.api.model.Category;
import tekup.android.foodup.api.network.CategoriesResponse;

public interface CategoriesAPICall {
    @GET("categories/")
    Call<CategoriesResponse> getCategories();
}
