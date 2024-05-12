package tekup.android.foodup.api.network;

public class CountResponse {
    private int count;
    public CountResponse() {
    }
    public CountResponse(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CountResponse{" +
                "count=" + count +
                '}';
    }
}
