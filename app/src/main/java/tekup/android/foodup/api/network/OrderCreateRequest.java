package tekup.android.foodup.api.network;

import java.util.List;

import tekup.android.foodup.api.model.OrderItem;

public class OrderCreateRequest {
    private List<OrderItem> orderItems;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
