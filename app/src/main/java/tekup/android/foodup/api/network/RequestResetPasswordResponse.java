package tekup.android.foodup.api.network;

public class RequestResetPasswordResponse {
    private String message;

    public RequestResetPasswordResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
