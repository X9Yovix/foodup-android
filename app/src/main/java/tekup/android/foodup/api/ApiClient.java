package tekup.android.foodup.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tekup.android.foodup.api.interfaces.AuthAPICall;
import tekup.android.foodup.api.interfaces.CategoriesAPICall;

public class ApiClient {
    //private static final String BASE_URL = "http://localhost:4000/api/";
    private static final String BASE_URL = "http://10.0.2.2:5198/api/";
    private static Retrofit retrofit;

    public static <T> T getApiService(Class<T> serviceClass, String jwtToken) {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", "Bearer " + jwtToken);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(serviceClass);
    }

}