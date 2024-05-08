package tekup.android.foodup.api.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tekup.android.foodup.api.model.Category;

public class CategoriesResponse {

    @SerializedName("categories")
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}