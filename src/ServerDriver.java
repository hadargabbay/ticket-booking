import controllers.ControllerRegistry;
import dao.DaoFileImpl;
import dao.IDao;
import algorithms.CheapestSeatAlgo;
import algorithms.IAlgoBooking;
import server.RequestDispatcher;
import server.Server;
import service.BookingService;

import java.util.Scanner;

/**
 * Application entry point for the ticket booking server.
 * Wires dependencies, loads controllers at startup, and starts the Server.
 */
public class ServerDriver {

    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) {
        try {
            // 1. Initialize DAO and algorithm
            IDao dao = new DaoFileImpl();
            IAlgoBooking algorithm = new CheapestSeatAlgo();
            BookingService bookingService = new BookingService(dao, algorithm);

            // 2. Load controllers at startup (Factory Pattern)
            ControllerRegistry controllerRegistry = new ControllerRegistry();
            controllerRegistry.loadControllers(bookingService);

            // 3. Create request dispatcher (separation layer between network and services)
            RequestDispatcher requestDispatcher = new RequestDispatcher(controllerRegistry);

            // 4. Create and start server
            int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
            Server server = new Server(port, requestDispatcher);

            System.out.println("--- Ticket Booking Server starting ---");
            server.start();
            System.out.println("Server is ready. Press Enter to stop.");

            // Wait for shutdown signal
            new Scanner(System.in).nextLine();
            server.stop();
            System.out.println("Server stopped.");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
