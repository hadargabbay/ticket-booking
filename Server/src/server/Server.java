package server;

import dto.ApiRequest;
import dto.ApiResponse;
import json.JsonManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TCP server that listens for incoming JSON requests.
// Runs the accept loop on a dedicated thread and handles each client on a separate thread.
public class Server {
    private final int port;
    private final RequestDispatcher requestDispatcher;
    private final JsonManager jsonManager;
    private ServerSocket serverSocket;
    private Thread acceptThread;
    private ExecutorService clientExecutor;
    private volatile boolean running;

    // Creates a server that will listen on the given port and route work through the dispatcher.
    public Server(int port, RequestDispatcher requestDispatcher) {
        this.port = port;
        this.requestDispatcher = requestDispatcher;
        this.jsonManager = JsonManager.getInstance();
    }

    // Opens the listening port and starts a background thread that accepts clients; each client is handled on its own thread.
    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        clientExecutor = Executors.newCachedThreadPool();

        acceptThread = new Thread(() -> {
            System.out.println("Server listening on port " + port + "...");
            while (running && serverSocket != null && !serverSocket.isClosed()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    clientExecutor.submit(() -> handleClient(clientSocket));
                } catch (IOException e) {
                    if (running) {
                        System.err.println("Accept error: " + e.getMessage());
                    }
                }
            }
        }, "Server-Accept-Thread");

        acceptThread.start();
    }

    // Stops accepting new clients, shuts down the thread pool, and closes the listening socket.
    public void stop() {
        running = false;
        if (clientExecutor != null) {
            clientExecutor.shutdown();
        }
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }
        if (acceptThread != null) {
            try {
                acceptThread.join(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Returns the TCP port this server is configured to use.
    public int getPort() {
        return port;
    }

    // Tells whether the accept loop is still marked as running.
    public boolean isRunning() {
        return running;
    }

    // Handles one client connection: reads one JSON line, runs it through the dispatcher, sends the JSON reply, then closes.
    private void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

            String line = in.readLine();
            if (line == null || line.isBlank()) {
                sendResponse(out, ApiResponse.error("Empty request"));
                return;
            }

            ApiRequest request = jsonManager.fromJson(line, ApiRequest.class);
            ApiResponse response = requestDispatcher.dispatch(request);
            sendResponse(out, response);

        } catch (Exception e) {
            System.err.println("Client handler error: " + e.getMessage());
            try {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                sendResponse(out, ApiResponse.error("Server error: " + e.getMessage()));
            } catch (IOException ignored) {
            }
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    // Writes one JSON line (the API response) to the client socket.
    private void sendResponse(PrintWriter out, ApiResponse response) {
        String json = jsonManager.toJson(response);
        out.println(json);
    }
}
