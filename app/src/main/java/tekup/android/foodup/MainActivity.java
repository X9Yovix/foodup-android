package tekup.android.foodup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.auth0.android.jwt.JWT;

import tekup.android.foodup.api.utility.JwtManager;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String jwtToken = JwtManager.getJwtToken(this);
        if (jwtToken != null && JwtManager.isValidToken(jwtToken)) {
            JWT jwt = new JWT(jwtToken);
            String roleUser = jwt.getClaim("http://schemas.microsoft.com/ws/2008/06/identity/claims/role").asString();
            if ("admin".equals(roleUser)) {
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }, SPLASH_DISPLAY_LENGTH);
            } else {
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }, SPLASH_DISPLAY_LENGTH);
            }
        } else {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }, SPLASH_DISPLAY_LENGTH);
        }
    }
}