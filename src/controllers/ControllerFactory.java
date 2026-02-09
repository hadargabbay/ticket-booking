package controllers;

import service.BookingService;

public class ControllerFactory
{

    // מתודה סטטית לקבלת ה-Controller המתאים
    public static IController<?> getController(String type, BookingService service) {
        if (type == null) {
            return null;
        }

        if (type.equalsIgnoreCase("TICKET")) {
            return new TicketController(service);
        }

        return null;
    }

}
