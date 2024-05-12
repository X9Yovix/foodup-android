package tekup.android.foodup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tekup.android.foodup.api.ApiClient;
import tekup.android.foodup.api.interfaces.AuthAPICall;
import tekup.android.foodup.api.network.ResetPasswordRequest;
import tekup.android.foodup.api.network.ResetPasswordResponse;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private Button buttonReset;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonReset = (Button) findViewById(R.id.buttonReset);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_reset_password);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_reset_password) {
                return true;
            } else if (id == R.id.menu_login) {
                startActivity(new Intent(ResetPasswordActivity.this, SignInActivity.class));
                overridePendingTransition(R.anim.slide_in_left_bottom_menu, R.anim.slide_out_right_bottom_menu);
                finish();
                return true;
            } else if (id == R.id.menu_create_account) {
                startActivity(new Intent(ResetPasswordActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.slide_in_left_bottom_menu, R.anim.slide_out_right_bottom_menu);
                finish();
                return true;
            } else {
                return false;
            }
        });

        buttonReset.setOnClickListener(v -> {
            if (!validateForm()) {
                return;
            }
            ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest.Builder()
                    .setEmail(editTextEmail.getText().toString())
                    .build();
            AuthAPICall authAPICall = ApiClient.getApiService(AuthAPICall.class, "");
            Call<ResetPasswordResponse> call = authAPICall.resetPassword(resetPasswordRequest);
            call.enqueue(new Callback<ResetPasswordResponse>() {
                @Override
                public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                    if (response.isSuccessful()) {
                        ResetPasswordResponse responseObject = response.body();
                        if (responseObject != null) {
                            System.out.println("responseObject: " + responseObject);
                            Toast.makeText(ResetPasswordActivity.this, responseObject.getMessage(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ResetPasswordActivity.this, ApplyResetPasswordActivity.class);
                            intent.putExtra("email", editTextEmail.getText().toString());
                            startActivity(intent);
                        }
                    } else {
                        System.out.println("response not succ: " + response);
                    }
                }

                @Override
                public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                    System.out.println("Throwable: " + t);
                }
            });
        });
    }

    private boolean validateForm() {
        String email = editTextEmail.getText().toString().trim();

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

        return true;
    }
}