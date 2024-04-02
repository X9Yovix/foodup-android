package tekup.android.foodup;

import androidx.appcompat.app.AppCompatActivity;

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
import tekup.android.foodup.api.auth.AuthAPICall;
import tekup.android.foodup.api.network.LoginRequest;
import tekup.android.foodup.api.network.LoginResponse;

public class SignInActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private ImageButton imageButtonShowPassword;
    private EditText editTextPassword;
    private Button buttonSignIn;
    private BottomNavigationView bottomNavigationView;
    private boolean isPasswordVisible = false;
    private int cursorPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        imageButtonShowPassword = (ImageButton) findViewById(R.id.imageButtonShowPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_login);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_login) {
                return true;
            } else if (id == R.id.menu_create_account) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.slide_in_right_bottom_menu, R.anim.slide_out_left_bottom_menu);
                finish();
                return true;
            } else if (id == R.id.menu_reset_password) {
                startActivity(new Intent(SignInActivity.this, ResetPasswordActivity.class));
                overridePendingTransition(R.anim.slide_in_right_bottom_menu, R.anim.slide_out_left_bottom_menu);
                finish();
                return true;
            } else {
                return false;
            }
        });

        imageButtonShowPassword.setOnClickListener(v ->

        {
            isPasswordVisible = !isPasswordVisible;
            cursorPosition = editTextPassword.getSelectionStart();
            if (isPasswordVisible) {
                editTextPassword.setTransformationMethod(null);
                imageButtonShowPassword.setImageResource(R.drawable.outline_visibility_off_24);
            } else {
                editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageButtonShowPassword.setImageResource(R.drawable.outline_visibility_24);
            }
            editTextPassword.setSelection(cursorPosition);
        });

        buttonSignIn.setOnClickListener(v ->

        {
            if (!validateForm()) {
                return;
            }
            LoginRequest loginRequest = new LoginRequest.Builder()
                    .setEmail(editTextEmail.getText().toString())
                    .setPassword(editTextPassword.getText().toString())
                    .build();
            AuthAPICall authAPICall = ApiClient.getApiService();
            Call<LoginResponse> call = authAPICall.login(loginRequest);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse responseObject = response.body();
                        if (responseObject != null) {
                            System.out.println("responseObject: " + responseObject);
                            Toast.makeText(SignInActivity.this, responseObject.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        System.out.println("response not succ: " + response);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    System.out.println("Throwable: " + t);
                }
            });
        });
    }

    private boolean validateForm() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

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

        return true;
    }
}