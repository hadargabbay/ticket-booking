package dto;

import com.google.gson.JsonElement;

/**
 * Represents an API response to be serialized and sent over the network.
 */
public class ApiResponse {
    private boolean success;
    private String message;
    private JsonElement data;

    /** Empty response for tools that need a default constructor. */
    public ApiResponse() {
    }

    /** Creates a response with only success flag and message (no extra data). */
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }

    /** Creates a full response including optional JSON data (for example a list of shows). */
    public ApiResponse(boolean success, String message, JsonElement data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /** Quick factory for a successful reply without a payload. */
    public static ApiResponse success(String message) {
        return new ApiResponse(true, message);
    }

    /** Quick factory for a successful reply that includes JSON data. */
    public static ApiResponse success(String message, JsonElement data) {
        return new ApiResponse(true, message, data);
    }

    /** Quick factory for a failed reply with an error message. */
    public static ApiResponse error(String message) {
        return new ApiResponse(false, message);
    }

    /** Returns whether the server considers the operation successful. */
    public boolean isSuccess() {
        return success;
    }

    /** Changes the success flag (rarely needed after construction). */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /** Returns the human-readable status or error text. */
    public String getMessage() {
        return message;
    }

    /** Updates the message text. */
    public void setMessage(String message) {
        this.message = message;
    }

    /** Returns extra JSON attached to the response (lists, objects, etc.). */
    public JsonElement getData() {
        return data;
    }

    /** Attaches or replaces the JSON data block. */
    public void setData(JsonElement data) {
        this.data = data;
    }
}
