package tekup.android.foodup.api.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class ProductManager {
    private static final String SHARED_PREF_NAME = "FOODUP_PRODUCT";
    private static final String PRODUCT_IDS_KEY = "FOODUP_PRODUCT_IDS";

    public static void saveProductId(Context context, int productId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String existingIds = sharedPreferences.getString(PRODUCT_IDS_KEY, "");
        String updatedIds = existingIds + productId + ",";

        editor.putString(PRODUCT_IDS_KEY, updatedIds);
        editor.apply();
    }

    public static String getProductIds(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PRODUCT_IDS_KEY, "");
    }

    public static void clearProductIds(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PRODUCT_IDS_KEY);
        editor.apply();
    }

    public static void removeProductId(Context context, int productId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String existingIds = sharedPreferences.getString(PRODUCT_IDS_KEY, "");

        StringBuilder updatedIdsBuilder = new StringBuilder();
        String[] ids = existingIds.split(",");
        for (String id : ids) {
            if (!id.isEmpty() && Integer.parseInt(id) != productId) {
                updatedIdsBuilder.append(id).append(",");
            }
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PRODUCT_IDS_KEY, updatedIdsBuilder.toString());
        editor.apply();
    }
}

