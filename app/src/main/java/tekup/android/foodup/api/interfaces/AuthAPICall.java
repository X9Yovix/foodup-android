package tekup.android.foodup.api.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import tekup.android.foodup.api.network.LoginRequest;
import tekup.android.foodup.api.network.LoginResponse;
import tekup.android.foodup.api.network.RegisterRequest;
import tekup.android.foodup.api.network.RegisterResponse;
import tekup.android.foodup.api.network.RequestResetPasswordRequest;
import tekup.android.foodup.api.network.RequestResetPasswordResponse;
import tekup.android.foodup.api.network.ResetPasswordUpdateRequest;
import tekup.android.foodup.api.network.ResetPasswordUpdateResponse;
import tekup.android.foodup.api.network.ResetPasswordVerificationCodeRequest;
import tekup.android.foodup.api.network.ResetPasswordVerificationCodeResponse;

public interface AuthAPICall {
    @POST("users/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("users/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("users/request-reset")
    Call<RequestResetPasswordResponse> requestResetPassword(@Body RequestResetPasswordRequest requestResetPasswordRequest);

    @POST("users/request-reset/verify")
    Call<ResetPasswordVerificationCodeResponse> verifyOtp(@Body ResetPasswordVerificationCodeRequest resetPasswordVerificationCodeRequest);

    @PUT("users/request-reset/update")
    Call<ResetPasswordUpdateResponse> updatePassword(@Body ResetPasswordUpdateRequest resetPasswordVerificationCodeRequest);
}