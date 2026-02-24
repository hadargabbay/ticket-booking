package controllers;

import service.BookingService;

/**
 * Factory for creating and loading controllers at application startup.
 * Implements the Factory Pattern to load the appropriate controllers.
 */
public class ControllerFactory {

    /**
     * Creates all known controllers and registers them in the registry.
     * Called at application startup by ControllerRegistry.
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
     * Creates a single controller by type (for backward compatibility).
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
