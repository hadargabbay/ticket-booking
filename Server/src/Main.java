import dao.DaoFileImpl;
import dao.IDao;
import algorithms.CheapestSeatAlgo;
import algorithms.FirstAvailableAlgo;
import models.*;
import service.BookingService;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for the Ticket Booking System console.
 * Manages system initialization, data persistence, and manual testing operations.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IDao dao = new DaoFileImpl();

        if (dao.getAllShows().isEmpty()) {
            initData(dao);
        }
        BookingService service = new BookingService(dao, new CheapestSeatAlgo());
        boolean running = true;
        while (running) {
            System.out.println("\n--- Ticket Booking System ---");
            System.out.println("1. Show All Shows");
            System.out.println("2. Book a Ticket");
            System.out.println("3. Use 'First Available' Algorithm");
            System.out.println("4. Use 'Cheapest Seat' Algorithm");
            System.out.println("5. Delete file and Restart ticket system");
            System.out.println("6. Save and Exit");
            System.out.print("Select option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1:
                    System.out.println("\n--- Shows Available ---");
                    for (Show s : dao.getAllShows()) {
                        List<Seat> seats = s.getSeats();
                        int total = seats.size();
                        int available = 0;
                        for (Seat seat : seats) {
                            if (seat.isAvailable()) {
                                available++;
                            }
                        }

                        double highPrice = seats.get(0).getPrice();
                        double regPrice = seats.get(10).getPrice();
                        double lowPrice = seats.get(total - 1).getPrice();
                        System.out.println("Show Name: " + s.getTitle());
                        System.out.println("Tickets: " + available + " left out of " + total);
                        System.out.println("Ticket Prices: First row: " + highPrice + " | Regular: " + regPrice + " | Discount: " + lowPrice);
                    }
                    break;

                case 2:
                    // Manual ticket booking process
                    System.out.print("Enter Show Name: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Customer ID: ");
                    int cid = scanner.nextInt();
                    scanner.nextLine(); // Clear the buffer
                    Customer customer = dao.getCustomer(cid);
                    // Register a new customer if not found in the database
                    if (customer == null) {
                        System.out.println("Customer ID " + cid + " is not in our system.");
                        System.out.println("Let's create a new profile.");
                        System.out.print("Please enter your name: ");
                        String cName = scanner.nextLine();
                        System.out.print("Enter Email: ");
                        String cEmail = scanner.nextLine();
                        System.out.print("Enter Phone: ");
                        String cPhone = scanner.nextLine();
                        Customer newCustomer = new Customer(cid, cName, cEmail, cPhone);
                        dao.saveCustomer(newCustomer);
                        System.out.println("Welcome! Your account has been created.");
                    }

                    // Attempt to book a seat using the selected parameters
                    List<models.Ticket> tickets = service.bookBestSeat(title, cid, "CHEAPEST", 1);
                    models.Ticket t = (tickets != null && !tickets.isEmpty()) ? tickets.get(0) : null;
                    if (t != null) {
                        System.out.println("Booked seat " + t.getSeat().getNumber() +
                                " for " + t.getShow().getTitle() + " (Price: " + t.getPrice() + ")");
                    } else {
                        System.out.println("Booking failed. Please check if the Show Name is correct and if seats are available.");
                    }
                    break;

                case 3:
                    // Switch the active booking strategy at runtime
                    service.setAlgorithm(new FirstAvailableAlgo());
                    System.out.println("Switched to: First Available Algorithm");
                    break;

                case 4:
                    // Switch the active booking strategy at runtime
                    service.setAlgorithm(new CheapestSeatAlgo());
                    System.out.println("Switched to: Cheapest Seat Algorithm");
                    break;

                case 5:
                    // Hard reset: delete the persistence file and re-initialize mock data
                    File file = new File("datasource.txt");
                    if (file.exists()) {
                        file.delete();
                    }
                    dao = new DaoFileImpl();
                    initData(dao);
                    service = new BookingService(dao, new CheapestSeatAlgo());
                    System.out.println("System Reset Complete. New shows generated.");
                    break;

                case 6:
                    // Safe shutdown: persist all data to the file
                    dao.saveData();
                    System.out.println("Data saved to datasource.txt. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    /**
     * Initializes the database with predefined mock data.
     * Generates shows with specific dates, times, and dynamic seating arrays.
     */
    private static void initData(IDao dao) {
        // Soccer Game - Scheduled for 20:30
        dao.saveShow(new Show(101, "Maccabi vs Hapoel", "Soccer game",
                LocalDateTime.of(2026, 5, 14, 20, 30), "Tel aviv", createSeats(50, 120.0)));

        // Kids Movie - Scheduled for 17:00
        dao.saveShow(new Show(102, "Frozen", "Movie",
                LocalDateTime.of(2026, 6, 10, 17, 0), "Cinema City", createSeats(40, 45.0)));

        // Regular Movie - Scheduled for 19:00
        dao.saveShow(new Show(103, "Lion King", "Movie",
                LocalDateTime.of(2026, 7, 1, 19, 0), "Cinema City", createSeats(100, 250.0)));

        // Default initial customer
        dao.saveCustomer(new Customer(1, "Avi", "aviavi@mail.com", "051-1234567"));

        dao.saveData();
    }

    /**
     * Helper method to generate seats for a show with dynamic pricing.
     * - First 5 seats: Premium price (x2)
     * - Last 5 seats: Discounted price (x0.8)
     * - Middle seats: Base price
     */
    private static List<Seat> createSeats(int count, double basePrice) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            double finalPrice = basePrice;

            if (i <= 5) {
                finalPrice = basePrice * 2; // Premium seating
            } else if (i > count - 5) {
                finalPrice = basePrice * 0.8; // Discount seating
            }

            seats.add(new Seat(1, i, finalPrice, true));
        }
        return seats;
    }
}