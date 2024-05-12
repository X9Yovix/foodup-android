package tekup.android.foodup.api.network;

public class ResetPasswordRequest {
    private String email;

    private ResetPasswordRequest(ResetPasswordRequest.Builder builder) {
        this.email = builder.email;
    }

    public String getEmail() {
        return email;
    }

    public static class Builder {
        private String email;

        public ResetPasswordRequest.Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public ResetPasswordRequest build() {
            return new ResetPasswordRequest(this);
        }
    }
}
