package tekup.android.foodup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tekup.android.foodup.R;
import tekup.android.foodup.api.model.Category;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categoryList;

    public CategoriesAdapter(Context context) {
        this.context = context;
        this.categoryList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void addAll(List<Category> categories) {
        categoryList.addAll(categories);
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCategoryName;
        private ImageView imageViewCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategoryName = itemView.findViewById(R.id.textViewCategoryName);
            imageViewCategory = itemView.findViewById(R.id.imageViewCategory);
        }

        public void bind(Category category) {
            textViewCategoryName.setText(category.getName());
            Picasso.get().load("http://10.0.2.2:5198/"+category.getImage()).into(imageViewCategory);
        }
    }
}
