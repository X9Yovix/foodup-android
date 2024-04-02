package tekup.android.foodup.api.network;

public class RequestResetPasswordRequest {
    private String email;

    private RequestResetPasswordRequest(RequestResetPasswordRequest.Builder builder) {
        this.email = builder.email;
    }

    public String getEmail() {
        return email;
    }

    public static class Builder {
        private String email;

        public RequestResetPasswordRequest.Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public RequestResetPasswordRequest build() {
            return new RequestResetPasswordRequest(this);
        }
    }
}
