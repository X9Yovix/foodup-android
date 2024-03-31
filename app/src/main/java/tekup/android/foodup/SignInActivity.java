package tekup.android.foodup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageButton;

public class SignInActivity extends AppCompatActivity {
    private ImageButton imageButtonShowPassword;
    private EditText editTextPassword;
    private boolean isPasswordVisible = false;
    private int cursorPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        imageButtonShowPassword = (ImageButton) findViewById(R.id.imageButtonShowPassword);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        imageButtonShowPassword.setOnClickListener(v -> {
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
    }
}