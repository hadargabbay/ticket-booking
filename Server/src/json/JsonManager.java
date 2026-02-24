package json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * Centralized JSON serialization and deserialization using GSON.
 * Handles Request/Response DTOs and domain models with proper type adapters.
 */
public class JsonManager {
    private static volatile JsonManager instance;
    private final Gson gson;

    private JsonManager() {
        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting();
        this.gson = builder.create();
    }

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

    /**
     * Serialize an object to JSON string.
     */
    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Deserialize JSON string to specified type.
     */
    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    /**
     * Deserialize JSON string to specified type using TypeToken for generics.
     */
    public <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    /**
     * Parse JSON string to JsonElement for flexible handling.
     */
    public JsonElement parse(String json) {
        return JsonParser.parseString(json);
    }

    /**
     * Get the underlying Gson instance for advanced usage.
     */
    public Gson getGson() {
        return gson;
    }
}
