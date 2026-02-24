=========================================================
Ticket Booking System - Client Application (JavaFX)
=========================================================

Overview:
---------
This is the Client-side application for the Ticket Booking System.
It provides a modern, user-friendly Graphical User Interface (GUI) built with JavaFX.
The client follows the MVC (Model-View-Controller) architecture and communicates
with the Server via TCP Sockets using JSON serialization.

Key Features & UI Bonuses:
--------------------------
1. Dynamic Display: Real-time table showing all available shows, including a dynamic
   calculation of "Tickets Sold / Total Seats".
2. Smart Booking & Payment: Users can select an algorithm (Cheapest / First Available)
   and book multiple tickets. The system includes a simulated secure payment gateway.
3. Rollback Mechanism: If a user cancels the payment process, the client immediately
   sends a "CANCEL_BOOKING" request to the server to free the locked seats (Atomicity).
4. Hidden Admin Mode: A restricted Admin Panel can be unlocked using a password.
    * Admin Password: admin123
    * Admin Capabilities: Soft-reset a show (cancel all tickets), hard-delete a show,
      and add new shows with dynamic pricing tiers.

Architecture Highlights:
------------------------
* NetworkClient (Singleton): Ensures a single, persistent socket connection manager
  across all JavaFX views.
* Gson Integration: Uses a custom `LocalDateTimeAdapter` to seamlessly parse dates
  sent from the server.
* Loosely Coupled: The UI (FXML) is completely separated from the networking logic,
  adhering to strict OOP and Design Pattern principles.

How to Run:
-----------
IMPORTANT: Ensure the Server application is running FIRST on port 8080. 
1. Run the `ServerDriver.java` class.
2. Run the `ClientDriver.java` class.
3. Use the interface to book tickets or enter the Admin mode to manage the system.