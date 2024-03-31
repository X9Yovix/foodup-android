package tekup.android.foodup.api.auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import tekup.android.foodup.api.network.LoginRequest;
import tekup.android.foodup.api.network.LoginResponse;

public interface AuthAPICall {
    @POST("auths/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}