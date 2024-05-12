package tekup.android.foodup.api.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tekup.android.foodup.api.network.CountResponse;
import tekup.android.foodup.api.network.OrderCreateRequest;
import tekup.android.foodup.api.network.OrderCreateResponse;

public interface OrdersAPICall {
    @POST("orders")
    Call<OrderCreateResponse> saveOrder(@Body OrderCreateRequest orderCreateRequest);
    @GET("orders/dashboard/count")
    Call<CountResponse> countOrders();
}