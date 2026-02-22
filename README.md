# Ticket booking system project

A Java-based system for booking show tickets. Supports managing customers, shows, and different ways to choose seats.

## Main Features

- **Seat Selection:** Choose between "Cheapest Seat" or "First Available" algorithms.
- **File Storage:** Data (shows, tickets, customers) is saved to `datasource.txt`.
- **Automatic Pricing:** Front-row premium, middle regular, back-row discount.
- **New Customer Setup:** Create a profile when booking with a new customer ID.
- **JSON API Server:** TCP server exposing booking operations over JSON (port 8080).

## Project Structure

| Package / File | Purpose |
|----------------|---------|
| `models/` | Domain objects: Seat, Show, Customer, Ticket |
| `dao/` | File-based persistence (read/write) |
| `service/` | Booking logic + algorithm integration |
| `algorithms/` | Seat selection strategies |
| `controllers/` | Separation layer between network and BookingService; ControllerFactory loads controllers at startup |
| `dto/` | Request/Response objects for the JSON API |
| `json/` | GSON-based serialization (JsonManager, LocalDateTimeAdapter) |
| `server/` | Server class, RequestDispatcher |
| `Main.java` | CLI menu |
| `ServerDriver.java` | Server entry point |

## Setup

### Prerequisites

- Java 17+
- Maven 3.x (for command-line build)

### Build

```bash
mvn compile
```

### Run

**CLI mode:**
```bash
mvn exec:java -Dexec.mainClass="Main"
```
Or run `Main.java` from your IDE.

**Server mode:**
```bash
mvn exec:java -Dexec.mainClass="ServerDriver"
```
Default port: 8080. Custom port: `mvn exec:java -Dexec.mainClass="ServerDriver" -Dexec.args="9090"`. Press Enter to stop the server.

**Test client (against running server):**
```bash
mvn exec:java -Dexec.mainClass="client.TestClient"
```

> **Tip:** Run `Main` once to initialize sample shows and customers in `datasource.txt` before testing the server.

### Dependencies

Managed by Maven (`pom.xml`):

- GSON 2.10.1 – JSON serialization
- JUnit 5 – Tests

## How to Use

### CLI (Main.java)

1. Run `Main.java`.
2. Use the menu to view shows and prices.
3. Choose a booking algorithm (options 3 or 4).
4. Enter show name and customer ID to book.
5. Exit with option 6 to save data.

### JSON API (ServerDriver)

Request format (one JSON object per line):

```json
{"action":"BOOK_BEST_SEAT","payload":{"showTitle":"Maccabi vs Hapoel","customerId":1}}
```

Response format:

```json
{"success":true,"message":"Best seat booked successfully","data":{...ticket...}}
```