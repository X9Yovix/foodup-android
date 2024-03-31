package tekup.android.foodup.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tekup.android.foodup.api.auth.AuthAPICall;

public class ApiClient {
    //private static final String BASE_URL = "http://localhost:4000/api/";
    private static final String BASE_URL = "http://10.0.2.2:4000/api/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static AuthAPICall getApiService() {
        return getRetrofitInstance().create(AuthAPICall.class);
    }
}