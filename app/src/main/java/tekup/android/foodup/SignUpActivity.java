package tekup.android.foodup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tekup.android.foodup.api.ApiClient;
import tekup.android.foodup.api.interfaces.AuthAPICall;
import tekup.android.foodup.api.network.RegisterRequest;
import tekup.android.foodup.api.network.RegisterResponse;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirmation;
    private ImageButton imageButtonShowPassword;
    private ImageButton imageButtonShowPasswordConfirmation;
    private SwitchCompat switchCompatGender;
    private Button buttonSignUp;
    private BottomNavigationView bottomNavigationView;
    private boolean isPasswordVisible = false, isPasswordConfirmationVisible = false;
    private int cursorPositionPassword = 0, cursorPositionPasswordConfirmation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPasswordConfirmation = (EditText) findViewById(R.id.editTextPasswordConfirmation);
        imageButtonShowPassword = (ImageButton) findViewById(R.id.imageButtonShowPassword);
        imageButtonShowPasswordConfirmation = (ImageButton) findViewById(R.id.imageButtonShowPasswordConfirmation);
        switchCompatGender = (SwitchCompat) findViewById(R.id.switchCompatGender);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_create_account);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_create_account) {
                return true;
            } else if (id == R.id.menu_login) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                overridePendingTransition(R.anim.slide_in_left_bottom_menu, R.anim.slide_out_right_bottom_menu);
                finish();
                return true;
            } else if (id == R.id.menu_reset_password) {
                startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
                overridePendingTransition(R.anim.slide_in_right_bottom_menu, R.anim.slide_out_left_bottom_menu);
                finish();
                return true;
            } else {
                return false;
            }
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

        buttonSignUp.setOnClickListener(v -> {
            if (!validateForm()) {
                return;
            }
            String firstName = editTextFirstName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String passwordConfirmation = editTextPasswordConfirmation.getText().toString();
            boolean isFemale = switchCompatGender.isChecked();
            String gender = isFemale ? "Female" : "Male";
            RegisterRequest registerRequest = new RegisterRequest.Builder()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setPassword(password)
                    .setPasswordConfirmation(passwordConfirmation)
                    .setGender(gender)
                    .build();
            AuthAPICall authAPICall = ApiClient.getApiService(AuthAPICall.class, "");
            Call<RegisterResponse> call = authAPICall.register(registerRequest);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful()) {
                        RegisterResponse responseObject = response.body();
                        if (responseObject != null) {
                            System.out.println("responseObject: " + responseObject.getMessage());
                            Toast.makeText(SignUpActivity.this, responseObject.getMessage(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUpActivity.this, VerifyAccount.class);
                            intent.putExtra("verifyEmail", email);
                            startActivity(intent);
                        }
                    } else {
                        System.out.println("response not succ: " + response);
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    System.out.println("Throwable: " + t);
                }
            });
        });
    }

    private boolean validateForm() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordConfirmation = editTextPasswordConfirmation.getText().toString().trim();

        if (firstName.isEmpty()) {
            editTextFirstName.setError("First name is required");
            editTextFirstName.requestFocus();
            return false;
        }

        if (lastName.isEmpty()) {
            editTextLastName.setError("Last name is required");
            editTextLastName.requestFocus();
            return false;
        }

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