package tekup.android.foodup.api.network;

public class ApplyResetPasswordResponse {
    private String message;

    public ApplyResetPasswordResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
