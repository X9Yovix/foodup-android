package tekup.android.foodup.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tekup.android.foodup.R;
import tekup.android.foodup.api.model.Food;

public class PopularFoodsAdapter extends RecyclerView.Adapter<PopularFoodsAdapter.ViewHolder> {

    private List<Food> popularFoods;

    public PopularFoodsAdapter(List<Food> popularFoods) {
        this.popularFoods = popularFoods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_item_popular_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = popularFoods.get(position);
        holder.bind(food);
    }

    @Override
    public int getItemCount() {
        return popularFoods.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView foodTextView;
        private ImageView foodImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodTextView = itemView.findViewById(R.id.foodTextView);
            foodImageView = itemView.findViewById(R.id.imageView);
        }

        public void bind(Food food) {
            foodTextView.setText(food.getName());
            Picasso.get().load(food.getImage()).into(foodImageView);
        }
    }
}


