package tekup.android.foodup.api.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tekup.android.foodup.api.model.Product;

public interface ProductsAPICall {
    @GET("products/")
    Call<List<Product>> getProducts(@Query("categories") List<String> categories, @Query("page") int page, @Query("pageSize") int pageSize);
}
