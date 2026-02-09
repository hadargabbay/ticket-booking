import controllers.ControllerFactory;
import controllers.IController;
import service.BookingService;
import dao.IDao;
import dao.DaoFileImpl;
import algorithms.IAlgoBooking;
import algorithms.CheapestSeatAlgo;
import models.Ticket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDriver {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            IDao dao = new DaoFileImpl();
            IAlgoBooking algorithm = new CheapestSeatAlgo();
            BookingService bookingService = new BookingService(dao, algorithm);
            IController<Ticket> ticketController = (IController<Ticket>) ControllerFactory.getController("TICKET", bookingService);

            System.out.println("--- Server is starting ---");

            try (ServerSocket serverSocket = new ServerSocket(8080)) {
                System.out.println("Server is listening on port 8080...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleClient(clientSocket, ticketController)).start();
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket socket, IController<Ticket> controller) {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            Object obj = in.readObject();
            if (obj instanceof Ticket) {
                Ticket ticketRequest = (Ticket) obj;

                //  קריאה ל-save ללא אחסון במשתנה כדי למנוע טעויות סוגים
                controller.save(ticketRequest);

                out.writeObject("Request processed successfully");
            }
            out.flush();
        } catch (Exception e) {
            System.err.println("Handler error: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (Exception e) {}
        }
    }
}
