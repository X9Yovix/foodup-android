package tekup.android.foodup.api.network;

public class ResetPasswordUpdateRequest {
    private String otp;
    private String email;
    private String password;

    private ResetPasswordUpdateRequest(ResetPasswordUpdateRequest.Builder builder) {
        this.otp = builder.otp;
    }

    public String getOtp() {
        return otp;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private String otp;
        private String email;
        private String password;

        public ResetPasswordUpdateRequest.Builder setOtp(String otp) {
            this.otp = otp;
            return this;
        }

        public ResetPasswordUpdateRequest.Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public ResetPasswordUpdateRequest.Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public ResetPasswordUpdateRequest build() {
            return new ResetPasswordUpdateRequest(this);
        }
    }
}
