package json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

// Centralized JSON serialization and deserialization using GSON.
// Handles Request/Response DTOs and domain models with proper type adapters.
public class JsonManager {
    private static volatile JsonManager instance;
    private final Gson gson;

    // Builds Gson with date support; use {@link #getInstance()} instead of calling this directly.
    private JsonManager() {
        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting();
        this.gson = builder.create();
    }

    // Returns the single shared JsonManager so the whole app uses the same Gson setup.
    public static JsonManager getInstance() {
        if (instance == null) {
            synchronized (JsonManager.class) {
                if (instance == null) {
                    instance = new JsonManager();
                }
            }
        }
        return instance;
    }

    // Turns any Java object into a JSON text string for sending over the socket.
    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    // Reads JSON text into a Java object of the class you pass in.
    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    // Same as the other fromJson, but for generic types where you must pass a full Type description.
    public <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    // Parses JSON into Gson's tree model when you need to inspect parts without a fixed class yet.
    public JsonElement parse(String json) {
        return JsonParser.parseString(json);
    }

    // Gives direct access to the configured Gson for code that needs features this wrapper does not expose.
    public Gson getGson() {
        return gson;
    }
}
