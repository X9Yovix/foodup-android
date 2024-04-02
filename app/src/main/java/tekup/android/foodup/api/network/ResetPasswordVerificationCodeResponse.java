package tekup.android.foodup.api.network;

public class ResetPasswordVerificationCodeResponse {
    private String message;

    public ResetPasswordVerificationCodeResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
