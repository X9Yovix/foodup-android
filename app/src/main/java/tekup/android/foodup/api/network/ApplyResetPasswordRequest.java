package tekup.android.foodup.api.network;

public class ApplyResetPasswordRequest {
    private String otp;
    private String email;
    private String password;
    private String passwordConfirmation;

    private ApplyResetPasswordRequest(ApplyResetPasswordRequest.Builder builder) {
        this.otp = builder.otp;
        this.email = builder.email;
        this.password = builder.password;
        this.passwordConfirmation = builder.passwordConfirmation;
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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public static class Builder {
        private String otp;
        private String email;
        private String password;
        private String passwordConfirmation;

        public ApplyResetPasswordRequest.Builder setOtp(String otp) {
            this.otp = otp;
            return this;
        }

        public ApplyResetPasswordRequest.Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public ApplyResetPasswordRequest.Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public ApplyResetPasswordRequest.Builder setPasswordConfirmation(String passwordConfirmation) {
            this.passwordConfirmation = passwordConfirmation;
            return this;
        }

        public ApplyResetPasswordRequest build() {
            return new ApplyResetPasswordRequest(this);
        }
    }
}
