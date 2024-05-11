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
import tekup.android.foodup.api.model.Product;
import tekup.android.foodup.api.utility.ProductManager;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;

    public ProductsAdapter(Context context) {
        this.context = context;
        this.productList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void addAll(List<Product> products) {
        productList.addAll(products);
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewProductName;
        private TextView textViewProductPrice;

        private ImageView imageViewProduct;
        private ImageView imageViewAddToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            imageViewAddToCart = itemView.findViewById(R.id.imageViewAddToCart);

            imageViewAddToCart.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Product product = productList.get(position);
                    int productId = product.getId();

                    String productIds = ProductManager.getProductIds(itemView.getContext());
                    if (!isProductIdStored(productIds, productId)) {
                        ProductManager.saveProductId(itemView.getContext(), productId);
                    } else {
                        ProductManager.removeProductId(itemView.getContext(), productId);
                    }
                    notifyItemChanged(position);
                }
            });
        }

        public void bind(Product product) {
            textViewProductName.setText(product.getName());
            textViewProductPrice.setText(String.valueOf(product.getPrice()));
            Picasso.get().load("http://10.0.2.2:5198/" + product.getImage()).into(imageViewProduct);

            int productId = product.getId();
            String productIds = ProductManager.getProductIds(itemView.getContext());
            if (isProductIdStored(productIds, productId)) {
                imageViewAddToCart.setImageResource(R.drawable.baseline_remove_shopping_cart_24);
            } else {
                imageViewAddToCart.setImageResource(R.drawable.baseline_add_shopping_cart_24);
            }
        }
    }

    private boolean isProductIdStored(String productIds, int productId) {
        if (productIds.isEmpty()) {
            return false;
        }
        String[] ids = productIds.split(",");
        for (String id : ids) {
            if (!id.isEmpty() && Integer.parseInt(id) == productId) {
                return true;
            }
        }
        return false;
    }

}
