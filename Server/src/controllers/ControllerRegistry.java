package controllers;

import service.BookingService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry of controllers loaded at application startup.
 * Controllers are created by ControllerFactory and stored here for dispatch.
 */
public class ControllerRegistry {
    private final Map<String, IController<?>> controllers = new ConcurrentHashMap<>();

    /**
     * Stores a controller under a short name (for example SHOW or TICKET) so the dispatcher can find it later.
     */
    public void register(String type, IController<?> controller) {
        if (type != null && controller != null) {
            controllers.put(type.toUpperCase(), controller);
        }
    }

    /**
     * Looks up the controller that was registered for this type string, or returns null if none exists.
     */
    public IController<?> getController(String type) {
        return type == null ? null : controllers.get(type.toUpperCase());
    }

    /**
     * Fills the registry with every controller the server knows about, using the shared booking service.
     */
    public void loadControllers(BookingService bookingService) {
        ControllerFactory.createAndRegister(this, bookingService);
    }
}
