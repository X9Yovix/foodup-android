package tekup.android.foodup.api.interfaces;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tekup.android.foodup.api.model.Category;
import tekup.android.foodup.api.network.CategoriesResponse;

public interface CategoriesAPICall {
    @GET("categories/")
    //Call<CategoriesResponse> getCategories();
    Call<CategoriesResponse> getCategories(@Query("pageNumber") int page, @Query("pageSize") int pageSize);
}
