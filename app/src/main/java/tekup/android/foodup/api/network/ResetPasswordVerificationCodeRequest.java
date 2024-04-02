package tekup.android.foodup.api.network;

public class ResetPasswordVerificationCodeRequest {

    private String otp;

    private ResetPasswordVerificationCodeRequest(ResetPasswordVerificationCodeRequest.Builder builder) {
        this.otp = builder.otp;
    }

    public String getOtp() {
        return otp;
    }

    public static class Builder {
        private String otp;

        public ResetPasswordVerificationCodeRequest.Builder setOtp(String otp) {
            this.otp = otp;
            return this;
        }

        public ResetPasswordVerificationCodeRequest build() {
            return new ResetPasswordVerificationCodeRequest(this);
        }
    }
}
