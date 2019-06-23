package keystore.client;

import com.google.gson.Gson;
import keystore.client.models.ErrorResponse;
import keystore.client.models.KeyValue;
import keystore.client.services.KeyValueStoreService;
import keystore.client.services.KeyValueStoreServiceFactory;
import keystore.client.services.WatchService;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collection;

public class App {

    private static KeyValueStoreService keyValueStoreService = KeyValueStoreServiceFactory.getKeyValueStoreService();

    public static void main(String[] args) {
        if (args.length == 0) {
            showUsage();
            System.exit(1);
        }

        String command = args[0];

        try {

            String key;
            switch (command.toLowerCase()) {
                case "get-all":
                    getAll();
                    break;
                case "get":
                    key = getArgument(args, 1, "key");
                    get(key);
                    break;
                case "set":
                    key = getArgument(args, 1, "key");
                    String value = getArgument(args, 2, "value");
                    set(key, value);
                    break;
                case "delete":
                    key = getArgument(args, 1, "key");
                    delete(key);
                    break;
                case "watch":
                    watch();
                    break;
                default:
                    System.err.println("Unknown command: " + command);
                    showUsage();
                    System.exit(1);
            }
        } catch (IOException ex) {
            System.err.println("Error wile processing command: " + ex.getMessage());
            System.exit(1);
        }
    }

    private static String getArgument(String[] args, int i, String name) {
        if (args.length < i + 1) {
            System.err.printf("Error: %s argument is required\n", name);
            System.exit(1);
        }

        return args[i];
    }

    private static void showUsage() {
        System.out.println(
                "Usage: keystore-client COMMAND args...\n" +
                        "Where:\n" +
                        "COMMAND may be:\n" +
                        "    get-all              Gets all key value stored in the server\n" +
                        "    get    <key>         Fetches the value of the given key from keystore server.\n" +
                        "    set    <key> <value> Set the value of the key in keystore server.\n" +
                        "    delete <key>         Deletes the given key from keystore server.\n" +
                        "    watch                Watches keystore server for changes\n");
    }

    private static void getAll() throws IOException {
        Response<Collection<KeyValue>> response = keyValueStoreService.getAll().execute();

        if (response.isSuccessful()) {
            for (KeyValue entry : response.body()) {
                System.out.printf("%s => %s\n", entry.getKey(), entry.getValue());
            }
        } else {
            handleError(response);
        }
    }

    private static void get(String key) throws IOException {
        Response<KeyValue> response = keyValueStoreService.get(key).execute();
        if (response.isSuccessful()) {
            System.out.println(response.body().getValue());
        } else {
            handleError(response);
        }
    }

    private static void set(String key, String value) throws IOException {
        Response<String> response = keyValueStoreService.add(new KeyValue(key, value)).execute();
        if (response.isSuccessful()) {
            System.out.println(response.body());
        } else {
            handleError(response);
        }
    }

    private static void delete(String key) throws IOException {
        Response<String> response = keyValueStoreService.delete(key).execute();
        if (response.isSuccessful()) {
            System.out.println(response.body());
        } else {
            handleError(response);
        }
    }

    private static void watch() {
        WatchService watchService = new WatchService();
        watchService.watch(changeEvent -> {

            KeyValue keyValue = changeEvent.getEntry();

            String keyValueString = keyValue == null ? "<non-existent>" : String.format("%s => %s",
                    keyValue.getKey(),
                    keyValue.getValue());

            System.out.printf("%s %s\n", changeEvent.getType(), keyValueString);
        });
    }

    private static void handleError(Response<?> response) throws IOException {
        System.out.println(parseError(response.errorBody().string()).getMessage());
        System.exit(1);
    }


    private static ErrorResponse parseError(String json) {
        return new Gson().fromJson(json, ErrorResponse.class);
    }
}
