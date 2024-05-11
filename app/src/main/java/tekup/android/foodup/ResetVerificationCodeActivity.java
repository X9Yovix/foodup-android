package tekup.android.foodup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tekup.android.foodup.api.ApiClient;
import tekup.android.foodup.api.interfaces.AuthAPICall;
import tekup.android.foodup.api.network.ResetPasswordUpdateRequest;
import tekup.android.foodup.api.network.ResetPasswordUpdateResponse;
import tekup.android.foodup.api.network.ResetPasswordVerificationCodeRequest;
import tekup.android.foodup.api.network.ResetPasswordVerificationCodeResponse;

public class ResetVerificationCodeActivity extends AppCompatActivity {
    private EditText editTextCode1;
    private EditText editTextCode2;
    private EditText editTextCode3;
    private EditText editTextCode4;
    private EditText editTextCode5;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirmation;
    private ImageButton imageButtonShowPassword;
    private ImageButton imageButtonShowPasswordConfirmation;
    private Button buttonValidate;
    private Button buttonReset;
    private LinearLayout containerChangePassword;
    private LinearLayout containerOtpCode;
    private boolean isPasswordVisible = false, isPasswordConfirmationVisible = false;
    private int cursorPositionPassword = 0, cursorPositionPasswordConfirmation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_verification_code);

        containerChangePassword = (LinearLayout) findViewById(R.id.containerChangePassword);
        containerOtpCode = (LinearLayout) findViewById(R.id.containerOtpCode);
        editTextCode1 = (EditText) findViewById(R.id.editTextCode1);
        editTextCode2 = (EditText) findViewById(R.id.editTextCode2);
        editTextCode3 = (EditText) findViewById(R.id.editTextCode3);
        editTextCode4 = (EditText) findViewById(R.id.editTextCode4);
        editTextCode5 = (EditText) findViewById(R.id.editTextCode5);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPasswordConfirmation = (EditText) findViewById(R.id.editTextPasswordConfirmation);

        imageButtonShowPassword = (ImageButton) findViewById(R.id.imageButtonShowPassword);
        imageButtonShowPasswordConfirmation = (ImageButton) findViewById(R.id.imageButtonShowPasswordConfirmation);

        buttonValidate = (Button) findViewById(R.id.buttonValidate);
        buttonReset = (Button) findViewById(R.id.buttonReset);

        editTextCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    editTextCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    editTextCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    editTextCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    editTextCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextCode1.setOnLongClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = clipboardManager.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0) {
                ClipData.Item item = clipData.getItemAt(0);
                if (item != null && item.getText() != null) {
                    String clipboardText = item.getText().toString().trim();
                    if (isValidCode(clipboardText)) {
                        String[] codeParts = clipboardText.split("(?!^)");
                        //if (codeParts.length == 5) {
                            editTextCode1.setText(codeParts[0]);
                            editTextCode2.setText(codeParts[1]);
                            editTextCode3.setText(codeParts[2]);
                            editTextCode4.setText(codeParts[3]);
                            editTextCode5.setText(codeParts[4]);
                        //}
                    }
                }
            }
            return true;
        });


        buttonValidate.setOnClickListener(v -> {

            String otpCode = editTextCode1.getText().toString()
                    + editTextCode2.getText().toString()
                    + editTextCode3.getText().toString()
                    + editTextCode4.getText().toString()
                    + editTextCode5.getText().toString();

            editTextCode1.setEnabled(false);
            editTextCode2.setEnabled(false);
            editTextCode3.setEnabled(false);
            editTextCode4.setEnabled(false);
            editTextCode5.setEnabled(false);
            /*
            String emailIntent = getIntent().getStringExtra("email");
            editTextEmail.setText(emailIntent);

            Animation fadeOutAnimation = AnimationUtils.loadAnimation(ResetVerificationCodeActivity.this, R.anim.button_validate);
            buttonValidate.startAnimation(fadeOutAnimation);
            buttonValidate.setVisibility(View.GONE);

            Animation slideUpFromBottomAnimation = AnimationUtils.loadAnimation(ResetVerificationCodeActivity.this, R.anim.otp_container);
            containerOtpCode.startAnimation(slideUpFromBottomAnimation);
            containerChangePassword.setVisibility(View.VISIBLE);
            Animation slideUpAnimation = AnimationUtils.loadAnimation(ResetVerificationCodeActivity.this, R.anim.change_pass_container);
            containerChangePassword.startAnimation(slideUpAnimation);

             */

            ResetPasswordVerificationCodeRequest resetPasswordVerificationCodeRequest = new ResetPasswordVerificationCodeRequest.Builder()
                    .setOtp(otpCode)
                    .build();
            AuthAPICall authAPICall = ApiClient.getApiService(AuthAPICall.class,"");
            Call<ResetPasswordVerificationCodeResponse> call = authAPICall.verifyOtp(resetPasswordVerificationCodeRequest);
            call.enqueue(new Callback<ResetPasswordVerificationCodeResponse>() {
                @Override
                public void onResponse(Call<ResetPasswordVerificationCodeResponse> call, Response<ResetPasswordVerificationCodeResponse> response) {
                    if (response.isSuccessful()) {
                        ResetPasswordVerificationCodeResponse responseObject = response.body();
                        if (responseObject != null) {
                            System.out.println("responseObject: " + responseObject);

                            Toast.makeText(ResetVerificationCodeActivity.this, "done", Toast.LENGTH_LONG).show();

                            String emailIntent = getIntent().getStringExtra("email");
                            editTextEmail.setText(emailIntent);

                            Animation fadeOutAnimation = AnimationUtils.loadAnimation(ResetVerificationCodeActivity.this, R.anim.button_validate);
                            buttonValidate.startAnimation(fadeOutAnimation);
                            buttonValidate.setVisibility(View.GONE);

                            Animation slideUpFromBottomAnimation = AnimationUtils.loadAnimation(ResetVerificationCodeActivity.this, R.anim.otp_container);
                            containerOtpCode.startAnimation(slideUpFromBottomAnimation);
                            containerChangePassword.setVisibility(View.VISIBLE);
                            Animation slideUpAnimation = AnimationUtils.loadAnimation(ResetVerificationCodeActivity.this, R.anim.change_pass_container);
                            containerChangePassword.startAnimation(slideUpAnimation);
                        }
                    } else {
                        System.out.println("response not succ: " + response);
                        editTextCode1.setEnabled(true);
                        editTextCode2.setEnabled(true);
                        editTextCode3.setEnabled(true);
                        editTextCode4.setEnabled(true);
                        editTextCode5.setEnabled(true);
                        containerChangePassword.setVisibility(View.GONE);
                        buttonValidate.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<ResetPasswordVerificationCodeResponse> call, Throwable t) {
                    System.out.println("Throwable: " + t);
                    editTextCode1.setEnabled(true);
                    editTextCode2.setEnabled(true);
                    editTextCode3.setEnabled(true);
                    editTextCode4.setEnabled(true);
                    editTextCode5.setEnabled(true);
                    containerChangePassword.setVisibility(View.GONE);
                    buttonValidate.setVisibility(View.VISIBLE);
                }
            });

        });

        imageButtonShowPassword.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            cursorPositionPassword = editTextPassword.getSelectionStart();
            if (isPasswordVisible) {
                editTextPassword.setTransformationMethod(null);
                imageButtonShowPassword.setImageResource(R.drawable.outline_visibility_off_24);
            } else {
                editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageButtonShowPassword.setImageResource(R.drawable.outline_visibility_24);
            }
            editTextPassword.setSelection(cursorPositionPassword);
        });

        imageButtonShowPasswordConfirmation.setOnClickListener(v -> {
            isPasswordConfirmationVisible = !isPasswordConfirmationVisible;
            cursorPositionPasswordConfirmation = editTextPasswordConfirmation.getSelectionStart();
            if (isPasswordConfirmationVisible) {
                editTextPasswordConfirmation.setTransformationMethod(null);
                imageButtonShowPasswordConfirmation.setImageResource(R.drawable.outline_visibility_off_24);
            } else {
                editTextPasswordConfirmation.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageButtonShowPasswordConfirmation.setImageResource(R.drawable.outline_visibility_24);
            }
            editTextPasswordConfirmation.setSelection(cursorPositionPasswordConfirmation);
        });

        buttonReset.setOnClickListener(v -> {
            if (!validateForm()) {
                return;
            }
            String email = editTextEmail.getText().toString();
            String otpCode = editTextCode1.getText().toString()
                    + editTextCode2.getText().toString()
                    + editTextCode3.getText().toString()
                    + editTextCode4.getText().toString()
                    + editTextCode5.getText().toString();
            String password = editTextPassword.getText().toString();
            String passwordConfirmation = editTextPasswordConfirmation.getText().toString();

            if (!email.isEmpty() && !otpCode.isEmpty() && !password.isEmpty() && !passwordConfirmation.isEmpty()) {
                if (password.equals(passwordConfirmation)) {
                    Toast.makeText(ResetVerificationCodeActivity.this, "done", Toast.LENGTH_SHORT).show();

                    ResetPasswordUpdateRequest resetPasswordUpdateRequest = new ResetPasswordUpdateRequest.Builder()
                            .setOtp(otpCode)
                            .setEmail(email)
                            .setPassword(password)
                            .build();
                    AuthAPICall authAPICall = ApiClient.getApiService(AuthAPICall.class,"");
                    Call<ResetPasswordUpdateResponse> call = authAPICall.updatePassword(resetPasswordUpdateRequest);
                    call.enqueue(new Callback<ResetPasswordUpdateResponse>() {
                        @Override
                        public void onResponse(Call<ResetPasswordUpdateResponse> call, Response<ResetPasswordUpdateResponse> response) {
                            if (response.isSuccessful()) {
                                ResetPasswordUpdateResponse responseObject = response.body();
                                if (responseObject != null) {
                                    System.out.println("responseObject: " + responseObject);
                                    Toast.makeText(ResetVerificationCodeActivity.this, responseObject.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                System.out.println("response not succ: " + response);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResetPasswordUpdateResponse> call, Throwable t) {
                            System.out.println("Throwable: " + t);
                        }
                    });

                } else {
                    Toast.makeText(ResetVerificationCodeActivity.this, "Verify entered password confirmation", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isValidCode(String code) {
        return code.matches("[a-zA-Z0-9]{5}");
    }

    private boolean validateForm() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordConfirmation = editTextPasswordConfirmation.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email address");
            editTextEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return false;
        }

        if (passwordConfirmation.isEmpty()) {
            editTextPasswordConfirmation.setError("Password confirmation is required");
            editTextPasswordConfirmation.requestFocus();
            return false;
        }

        if (!password.equals(passwordConfirmation)) {
            editTextPasswordConfirmation.setError("Passwords do not match");
            editTextPasswordConfirmation.requestFocus();
            return false;
        }

        if (password.length() < 8) {
            editTextPassword.setError("Password must be at least 8 characters");
            editTextPassword.requestFocus();
            return false;
        }
        return true;
    }
}
