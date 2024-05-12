package tekup.android.foodup.api.interfaces;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tekup.android.foodup.api.network.ProductsResponse;

public interface ProductsAPICall {
    @GET("products/category/{categoryId}")
    Call<ProductsResponse> getProductsByCategory(@Path("categoryId") int categoryId, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize);
    @POST("products/orders")
    Call<ProductsResponse> getProductsByIds(@Body List<Integer> productIds);
}
