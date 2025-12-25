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

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IDao dao = new DaoFileImpl();
        if (dao.getAllShows().isEmpty()) {// if file already build from previous runs don't run over it
            initData(dao);
        }
        BookingService service = new BookingService(dao, new CheapestSeatAlgo());// run first cheapest algo until changed
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
            scanner.nextLine(); // ignore \n
            switch (choice) {
                case 1: // show all shows and tickets left
                    System.out.println("\n--- Shows Available ---");
                    for (Show s : dao.getAllShows()) {
                        List<Seat> seats = s.getSeats();
                        int total = s.getSeats().size();
                        int available = 0;
                        for (Seat seat : s.getSeats()) {
                            if (seat.isAvailable()) {
                                available++;
                            }
                        }
                        double HighPrice = seats.get(0).getPrice();// get the different prices and show them, and show info
                        double regPrice = seats.get(10).getPrice();
                        double lowPrice = seats.get(total - 1).getPrice();
                        System.out.println("Show Name: " + s.getTitle());
                        System.out.println("Tickets: " + available + " left out of " + total);
                        System.out.println("Ticket Prices: First row: " + HighPrice + " | Regular: " + regPrice + " | Discount: " + lowPrice);
                    }
                    break;
                case 2: // book a ticket
                    System.out.print("Enter Show Name: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Customer ID: ");
                    int cid = scanner.nextInt();
                    scanner.nextLine(); // clean space
                    Customer customer = dao.getCustomer(cid);// get customer id, if not created it will be null
                    if (customer == null) {// create new customer for booking
                        System.out.println("Customer ID " + cid + " is not in our system.");
                        System.out.println("Let's create a new profile");
                        System.out.print("Please enter your name:");
                        String Cname = scanner.nextLine();
                        System.out.print("Enter Email:");
                        String Cemail = scanner.nextLine();
                        System.out.print("Enter Phone:");
                        String Cphone = scanner.nextLine();
                        Customer newCustomer = new Customer(cid, Cname, Cemail, Cphone);// after getting all the info save the Customer
                        dao.saveCustomer(newCustomer);
                        System.out.println("Welcome, Your account has been created.");
                    }
                    Ticket t = service.bookBestSeat(title, cid);// book the ticket if show found
                    if (t != null) {
                        System.out.println("Booked seat " + t.getSeat().getNumber() +
                                " for " + t.getShow().getTitle() + " (Price: " + t.getPrice() + ")");
                    }
                    else {
                        System.out.println("Booking failed. Please check if the Show Name is correct and if seats are available.");
                    }
                    break;
                case 3:// set algo to FirstAvailable
                    service.setAlgorithm(new FirstAvailableAlgo());
                    System.out.println("Switched to: First Available Algorithm");
                    break;
                case 4:// set algo to CheapstSeat
                    service.setAlgorithm(new CheapestSeatAlgo());
                    System.out.println("Switched to: Cheapest Seat Algorithm");
                    break;
                case 5: // delete file and seats
                    File file = new File("datasource.txt");
                    if (file.exists()) {
                        file.delete();
                    }
                    dao = new DaoFileImpl();// create clean start, reset shows to have seats and more
                    initData(dao);
                    service = new BookingService(dao, new CheapestSeatAlgo());
                    System.out.println("System Reset Complete");
                    break;
                case 6: // save and exit
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

    private static void initData(IDao dao) {// init, create shows and ticket amount using createSeats
        dao.saveShow(new Show(101, "Maccabi vs Hapoel", "Soccer game",
                LocalDateTime.now().plusDays(1), "Tel aviv", createSeats(50, 120.0)));

        dao.saveShow(new Show(102, "Frozen", "Movie",
                LocalDateTime.now().plusDays(2), "Cinema City", createSeats(40, 45.0)));

        dao.saveShow(new Show(103, "Lion King", "Movie",
                LocalDateTime.now().plusDays(5), "Cinema City", createSeats(100, 250.0)));

        dao.saveCustomer(new Customer(1, "Avi", "aviavi@mail.com", "051-1234567"));// first customer for file
        dao.saveData();
    }

    // func to create seats and set different prices
    private static List<Seat> createSeats(int count, double basePrice) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            double finalPrice = basePrice;
            if (i <= 5) {
                finalPrice = basePrice * 2; // higher priced tickets (5 tickets nearest to the show)
            }
            else if (i > count - 5) {
                finalPrice = basePrice * 0.8; // lowest price tickets (5 tickets which are farthest then the show)
            }
            seats.add(new Seat(1, i, finalPrice, true));// create each seat and updates its to the show, its price, etc
        }
        return seats;
    }
}