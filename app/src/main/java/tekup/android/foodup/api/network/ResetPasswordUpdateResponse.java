package tekup.android.foodup.api.network;

public class ResetPasswordUpdateResponse {
    private String message;

    public ResetPasswordUpdateResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
