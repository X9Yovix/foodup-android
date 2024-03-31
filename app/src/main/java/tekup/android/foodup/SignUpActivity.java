package tekup.android.foodup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tekup.android.foodup.api.ApiClient;
import tekup.android.foodup.api.auth.AuthAPICall;
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
            String password = editTextPassword.getText().toString();
            String passwordConfirmation = editTextPasswordConfirmation.getText().toString();
            if (!validatePasswordConfirmation(password, passwordConfirmation)) {
                Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            String firstName = editTextFirstName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            String email = editTextEmail.getText().toString();
            boolean isFemale = switchCompatGender.isChecked();
            String gender = isFemale ? "Female" : "Male";

            AuthAPICall authAPICall = ApiClient.getApiService();
            RegisterRequest registerRequest = new RegisterRequest.Builder()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setPassword(password)
                    .setGender(gender)
                    .build();
            System.out.println(registerRequest);
            Call<RegisterResponse> call = authAPICall.register(registerRequest);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful()) {
                        RegisterResponse responseObject = response.body();
                        if (responseObject != null) {
                            System.out.println("responseObject: " + responseObject.getMessage());
                            Toast.makeText(SignUpActivity.this, responseObject.getMessage(), Toast.LENGTH_SHORT).show();
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

    private boolean validatePasswordConfirmation(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }
}