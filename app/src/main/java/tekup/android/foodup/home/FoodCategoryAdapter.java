package tekup.android.foodup.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tekup.android.foodup.R;

public class FoodCategoryAdapter extends RecyclerView.Adapter<FoodCategoryAdapter.ViewHolder> {

    private List<String> foodCategories;

    public FoodCategoryAdapter(List<String> foodCategories) {
        this.foodCategories = foodCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_item_food_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String category = foodCategories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return foodCategories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
        }

        public void bind(String category) {
            categoryTextView.setText(category);
        }
    }
}

