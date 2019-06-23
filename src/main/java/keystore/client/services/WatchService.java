package keystore.client.services;

import com.google.gson.Gson;
import keystore.client.models.ChangeEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WatchService {

    private static String DEFAULT_URL = "ws://localhost:4567/watch";

    public void watch(ChangeListener cl) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(DEFAULT_URL).build();
        Gson gson = new Gson();
        WebSocket ws = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onMessage(WebSocket webSocket, String text) {
                ChangeEvent ce = gson.fromJson(text, ChangeEvent.class);
                cl.handle(ce);
            }
        });
    }
}
