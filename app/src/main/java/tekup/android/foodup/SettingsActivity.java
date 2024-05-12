package tekup.android.foodup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.auth0.android.jwt.JWT;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import tekup.android.foodup.api.utility.JwtManager;
import tekup.android.foodup.api.utility.ProductManager;

public class SettingsActivity extends AppCompatActivity {
    private TextView fullNameTextView;
    private TextView emailTextView;
    private TextView nbrProductsTextView;
    private BottomNavigationView bottomNavigationView;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_categories) {
                startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_left_bottom_menu, R.anim.slide_out_right_bottom_menu);
                finish();
                return true;
            } else if (id == R.id.menu_deals) {
                return true;
            } else if (id == R.id.menu_orders) {
                startActivity(new Intent(SettingsActivity.this, MyOrdersActivity.class));
                overridePendingTransition(R.anim.slide_in_left_bottom_menu, R.anim.slide_out_right_bottom_menu);
                finish();
                return true;
            } else if (id == R.id.menu_profile) {
                return true;
            } else {
                return false;
            }
        });

        fullNameTextView = (TextView) findViewById(R.id.fullNameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        nbrProductsTextView = (TextView) findViewById(R.id.nbrProductsTextView);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            JwtManager.logout(this);
            startActivity(new Intent(SettingsActivity.this, SignInActivity.class));
            finish();
        });

        String jwtToken = JwtManager.getJwtToken(this);
        if (jwtToken != null) {
            JWT jwt = new JWT(jwtToken);
            String firstName = jwt.getClaim("firstName").asString();
            String lastName = jwt.getClaim("lastName").asString();
            String email = jwt.getClaim("email").asString();
            String fullName = firstName + " " + lastName;
            fullNameTextView.setText(fullName);
            emailTextView.setText(email);
        }

        String productIds = ProductManager.getProductIds(this);
        if (!productIds.isEmpty()) {
            String[] ids = productIds.split(",");
            int numberOfSelectedProducts = ids.length;
            System.out.println(numberOfSelectedProducts);
            nbrProductsTextView.setText(String.valueOf(numberOfSelectedProducts) + " Items");
        } else {
            nbrProductsTextView.setText("0 Items");
        }
    }
}