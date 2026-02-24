package com.hit.ticketbookingclient.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hit.ticketbookingclient.models.ApiRequest;
import com.hit.ticketbookingclient.models.ApiResponse;
import com.hit.ticketbookingclient.models.LocalDateTimeAdapter;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

// Handles all socket communication
public class NetworkClient {
    private static NetworkClient instance;
    private final String serverAddress = "localhost";
    private final int serverPort = 8080;
    private final Gson gson;

    private NetworkClient() {
        this.gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
    }

    public static NetworkClient getInstance() {
        if (instance == null) {
            instance = new NetworkClient();
        }
        return instance;
    }

    // Opens a socket connection, sends a JSON request, and waits for the server's response
    public ApiResponse sendRequest(ApiRequest request) {
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String jsonRequest = gson.toJson(request);
            out.println(jsonRequest);
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                responseBuilder.append(line);
            }
            String fullJsonResponse = responseBuilder.toString();
            if (!fullJsonResponse.isEmpty()) {
                return gson.fromJson(fullJsonResponse, ApiResponse.class);
            }

        }
        catch (Exception e) {
            System.err.println("Communication or Parsing error: " + e.getMessage());
            return ApiResponse.error("Failed to connect or parse response.");
        }
        return ApiResponse.error("No response from server.");
    }
    public Gson getGson() {
        return gson;
    }
}