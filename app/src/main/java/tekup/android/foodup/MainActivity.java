package tekup.android.foodup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import tekup.android.foodup.api.utility.JwtManager;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String jwtToken = JwtManager.getJwtToken(this);
        if (jwtToken != null && JwtManager.isValidToken(jwtToken)) {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }, SPLASH_DISPLAY_LENGTH);
        } else {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }, SPLASH_DISPLAY_LENGTH);
        }

    }
}