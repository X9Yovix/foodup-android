package tekup.android.foodup.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

import tekup.android.foodup.R;
import tekup.android.foodup.api.model.Food;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //if (JwtManager.isUserLoggedIn(this)) {
        List<Food> popularFoods = Arrays.asList(
                new Food("Pizza","https://picsum.photos/200/300"),
                new Food("Burger","https://picsum.photos/200/300"),
                new Food("Sushi","https://picsum.photos/200/300"),
                new Food("Pasta","https://picsum.photos/200/300"),
                new Food("Tacos","https://picsum.photos/200/300")
        );

        List<String> foodCategories = Arrays.asList(
                "Breakfast",
                "Lunch",
                "Dinner",
                "Drinks"
        );
        RecyclerView popularFoodsRecyclerView = findViewById(R.id.popularFoodsRecyclerView);
        PopularFoodsAdapter adapter = new PopularFoodsAdapter(popularFoods);
        popularFoodsRecyclerView.setAdapter(adapter);


        RecyclerView categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        FoodCategoryAdapter foodCategoryAdapter = new FoodCategoryAdapter(foodCategories);
        categoryRecyclerView.setAdapter(foodCategoryAdapter);
        //} else {
        //    startActivity(new Intent(HomeActivity.this, SignInActivity.class));
        //    finish();
        //}
    }
}