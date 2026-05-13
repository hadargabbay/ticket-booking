package dto;

import com.google.gson.JsonElement;


// Represents an incoming API request over the network.
// Contains an action type and a JSON payload to be parsed by the appropriate controller.

public class ApiRequest {
    private String action;
    private JsonElement payload;

    // Empty request object for frameworks that need a no-arg constructor.
    public ApiRequest() {
    }

    // Builds a request with the command name and optional JSON body.
    public ApiRequest(String action, JsonElement payload) {
        this.action = action;
        this.payload = payload;
    }

    // Returns what the client wants the server to do (for example GET_ALL_SHOWS).
    public String getAction() {
        return action;
    }

    // Sets or changes the action name before sending.
    public void setAction(String action) {
        this.action = action;
    }

    // Returns the JSON body attached to this request, if any.
    public JsonElement getPayload() {
        return payload;
    }

    // Attaches or replaces the JSON body for this request.
    public void setPayload(JsonElement payload) {
        this.payload = payload;
    }
}
