package com.hit.ticketbookingclient.models;

import com.google.gson.JsonElement;

/**
 * Represents an incoming API request over the network.
 * Contains an action type and a JSON payload to be parsed by the appropriate controller.
 */
public class ApiRequest {
    private String action;
    private JsonElement payload;

    public ApiRequest() {
    }

    public ApiRequest(String action, JsonElement payload) {
        this.action = action;
        this.payload = payload;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public JsonElement getPayload() {
        return payload;
    }

    public void setPayload(JsonElement payload) {
        this.payload = payload;
    }
}
