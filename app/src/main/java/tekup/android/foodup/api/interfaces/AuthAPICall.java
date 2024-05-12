package tekup.android.foodup.api.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import tekup.android.foodup.api.network.CountResponse;
import tekup.android.foodup.api.network.LoginRequest;
import tekup.android.foodup.api.network.LoginResponse;
import tekup.android.foodup.api.network.RegisterRequest;
import tekup.android.foodup.api.network.RegisterResponse;
import tekup.android.foodup.api.network.ResetPasswordRequest;
import tekup.android.foodup.api.network.ResetPasswordResponse;
import tekup.android.foodup.api.network.ApplyResetPasswordRequest;
import tekup.android.foodup.api.network.ApplyResetPasswordResponse;
import tekup.android.foodup.api.network.VerifyOtpRequest;
import tekup.android.foodup.api.network.VerifyOtpResponse;

public interface AuthAPICall {
    @POST("users/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("users/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("users/verify-otp")
    Call<VerifyOtpResponse> verifyOtp(@Body VerifyOtpRequest verifyOtpRequest);

    @POST("users/reset-password")
    Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);

    @PUT("users/reset-password/apply")
    Call<ApplyResetPasswordResponse> applyResetPassword(@Body ApplyResetPasswordRequest applyResetPasswordRequest);

    @GET("users/dashboard/count")
    Call<CountResponse> countUsers();
}