# Ticket Booking System
A complete Client-Server Java application for booking show tickets. 

##  System Overview

The system is built using a modern **Client-Server architecture** communicating over TCP Sockets using JSON serialization (GSON). 

* **The Server** handles business logic, algorithms, multithreaded client requests, and data persistence.
* **The Client** provides a rich, loosely coupled Graphical User Interface (GUI) built with JavaFX, strictly following the MVC architecture.

##  Key Features & Bonuses

* **Seat Selection Algorithms (Strategy Pattern):** Users can dynamically choose between "Cheapest Seat" or "First Available" algorithms.
* **Atomic Transactions (Rollback):** If a user initiates a booking but cancels at the payment screen, a `CANCEL_BOOKING` request is instantly sent to the server to free the locked seats.
* **Admin Mode:** A hidden, password-protected admin panel (`Password: admin123`) allowing administrators to soft-reset shows or hard-delete them.
* **Factory & Registry Patterns:** Controllers on the server are dynamically loaded and managed via a Controller Registry.
* **Persistent Storage:** All data (shows, tickets, customers) is safely serialized and saved to `datasource.txt`.

##  Project Structure

The repository is organized into a Monorepo containing two separate Maven projects:

* `/Server` - Contains the Java backend, Models, DAOs, Services, Controllers, and JUnit Tests.
* `/Client` - Contains the JavaFX application, Network Singleton Manager, Views (FXML), and UI Controllers.

##  How to Run the Application

To experience the full system, you must run both the Server and the Client.

### Step 1: Start the Server
1. Open the `/Server` project in your IDE (IntelliJ / Eclipse).
2. Navigate to `src/main/java/ServerDriver.java` and run it.
3. The server will start listening on port `8080`.

### Step 2: Start the Client
1. Open the `/Client` project in your IDE.
2. Navigate to `src/main/java/com/hit/ticketbookingclient/ClientDriver.java` and run it.
3. The JavaFX GUI will launch, fetch the live data from the server, and you can start booking tickets!
