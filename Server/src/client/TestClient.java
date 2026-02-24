import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Simple test client for the ticket booking server.
 * Sends JSON requests and prints JSON responses.
 *
 * Usage: Run after starting ServerDriver. Ensure datasource has shows/customers
 * (e.g. run Main once to initialize data).
 *
 * Example requests:
 *   BOOK_BEST_SEAT: {"action":"BOOK_BEST_SEAT","payload":{"showTitle":"Maccabi vs Hapoel","customerId":1}}
 *   BOOK_TICKET: (requires full Ticket object in payload)
 */
public class TestClient {
    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 8080;

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server. Enter JSON request (one line), or 'quit' to exit.");
            System.out.println("Example: {\"action\":\"BOOK_BEST_SEAT\",\"payload\":{\"showTitle\":\"Maccabi vs Hapoel\",\"customerId\":1}}");

            while (true) {
                System.out.print("> ");
                String line = scanner.nextLine();
                if (line == null || "quit".equalsIgnoreCase(line.trim())) break;

                out.println(line);
                String response = in.readLine();
                if (response != null) {
                    System.out.println("Response: " + response);
                }
            }
        }
    }
}
