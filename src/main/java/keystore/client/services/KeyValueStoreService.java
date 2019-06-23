package keystore.client.services;

import keystore.client.models.KeyValue;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Collection;

public interface KeyValueStoreService {
    @POST("/keyvalue")
    Call<String> add(@Body KeyValue Value);

    @GET("/keyvalue")
    Call<Collection<KeyValue>> getAll();

    @GET("/keyvalue/{key}")
    Call<KeyValue> get(@Path("key") String Key);

    @DELETE("/keyvalue/{key}")
    Call<String> delete(@Path("key") String Key);
}