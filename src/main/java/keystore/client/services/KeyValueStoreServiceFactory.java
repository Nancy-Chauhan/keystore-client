package keystore.client.services;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KeyValueStoreServiceFactory {
    private static final String BASE_URL = "http://localhost:4567";

    private static Retrofit.Builder builder
            = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient
            = new OkHttpClient.Builder();

    private static KeyValueStoreService service = null;

    public static KeyValueStoreService getKeyValueStoreService() {
        if (service == null) {
            service = retrofit.create(KeyValueStoreService.class);
        }
        return service;
    }
}
