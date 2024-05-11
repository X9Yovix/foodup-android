package tekup.android.foodup.api.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tekup.android.foodup.api.model.Product;

public class ProductsResponse {
    @SerializedName("products")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
