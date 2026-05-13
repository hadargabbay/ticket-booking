package controllers;

import service.BookingService;

/**
 * Factory for creating and loading controllers at application startup.
 * Implements the Factory Pattern to load the appropriate controllers.
 */
public class ControllerFactory {

    /**
     * Builds the default show and ticket controllers and saves them in the registry under fixed names.
     */
    public static void createAndRegister(ControllerRegistry registry, BookingService service) {
        if (registry == null || service == null) {
            return;
        }
        registry.register("SHOW", new ShowController(service));
        registry.register("TICKET", new TicketController(service));
        // Add more controller types here as they are implemented
    }

    /**
     * Builds just one controller instance by type name (only TICKET is supported here today).
     */
    public static IController<?> getController(String type, BookingService service) {
        if (type == null || service == null) {
            return null;
        }
        if (type.equalsIgnoreCase("TICKET")) {
            return new TicketController(service);
        }
        return null;
    }
}
