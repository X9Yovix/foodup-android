package tekup.android.foodup.api.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class JwtManager {
    private static final String SHARED_PREF_NAME = "FOODUP";
    private static final String JWT_TOKEN_KEY = "FOODUPKEY";

    public static void saveJwtToken(Context context, String jwtToken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(JWT_TOKEN_KEY, jwtToken);
        editor.apply();
    }

    public static String getJwtToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(JWT_TOKEN_KEY, null);
    }

    public static boolean isUserLoggedIn(Context context) {
        return getJwtToken(context) != null;
    }

    public static void logout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(JWT_TOKEN_KEY);
        editor.apply();
    }
}
