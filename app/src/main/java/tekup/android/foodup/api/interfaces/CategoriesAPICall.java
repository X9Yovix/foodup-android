package tekup.android.foodup.api.interfaces;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tekup.android.foodup.api.network.CategoriesResponse;
import tekup.android.foodup.api.network.CountResponse;

public interface CategoriesAPICall {
    @GET("categories/")
    Call<CategoriesResponse> getCategories(@Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize);
    @GET("categories/dashboard/count")
    Call<CountResponse> countCategories();
}
