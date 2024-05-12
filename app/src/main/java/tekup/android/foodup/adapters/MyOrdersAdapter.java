package tekup.android.foodup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tekup.android.foodup.R;
import tekup.android.foodup.api.model.Product;

public class MyOrdersAdapter extends ArrayAdapter<Product> {
    private Context mContext;
    private int mResource;

    public MyOrdersAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        ViewHolder holder;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.productNameTextView = listItemView.findViewById(R.id.productNameTextView);
            holder.productPriceTextView = listItemView.findViewById(R.id.productPriceTextView);
            holder.productQuantityEditText = listItemView.findViewById(R.id.productQuantityEditText);
            holder.imageViewProduct = listItemView.findViewById(R.id.imageViewProduct);
            holder.incrementButton = listItemView.findViewById(R.id.incrementButton);
            holder.decrementButton = listItemView.findViewById(R.id.decrementButton);

            listItemView.setTag(holder);
        } else {
            holder = (ViewHolder) listItemView.getTag();
        }

        Product product = getItem(position);
        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText(String.valueOf(product.getPrice()));
        holder.productQuantityEditText.setText(String.valueOf(1));
        Picasso.get().load("http://10.0.2.2:5198/" + product.getImage()).into(holder.imageViewProduct);

        holder.incrementButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.productQuantityEditText.getText().toString());
            quantity++;
            holder.productQuantityEditText.setText(String.valueOf(quantity));
            product.setQuantity(quantity);
        });

        holder.decrementButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.productQuantityEditText.getText().toString());
            System.out.println("hadi qty "+ quantity);
            if (quantity > 1) {
                quantity--;
                holder.productQuantityEditText.setText(String.valueOf(quantity));
                product.setQuantity(quantity);
            }
        });

        return listItemView;
    }

    static class ViewHolder {
        TextView productNameTextView;
        TextView productPriceTextView;
        EditText productQuantityEditText;
        ImageView imageViewProduct;
        ImageButton incrementButton;
        ImageButton decrementButton;
    }
}
