# Ticket booking system project
This is a Java-based system for booking show tickets. 
It supports managing customers, shows, and different ways to choose seats.

# Main Features
Seat Selection: You can choose between two algorithms: "Cheapest Seat" or "First Available".
File Storage: All the data (shows, tickets, and customers) is saved to a file called datasource.txt so it stays there even after you close the program.
Automatic Pricing: The system sets prices based on the row: higher price for the front, regular for the middle, and a discount for the back.
New Customer Setup: If you try to book a ticket for an ID that doesn't exist, the system will let you create a new profile.

# Project Structure
models: Basic objects like Seat, Show, Customer, and Ticket.
dao: Handles reading and writing to the file.
service: The logic that connects the booking process and the algorithms.
algorithms: The different strategies for picking a seat.
Main.java: The main menu where you run everything.

# How to use
1) Run Main.java.
2) Use the menu to see available shows and prices.
3) Choose a booking algorithm (Options 3 or 4).
4) Enter show details and customer ID to book.
5) Exit using Option 6 to make sure everything is saved.