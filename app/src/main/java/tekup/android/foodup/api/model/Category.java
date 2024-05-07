package tekup.android.foodup.api.model;

public class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }
    public Category() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
