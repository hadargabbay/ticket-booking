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
     * Register a controller for the given type.
     */
    public void register(String type, IController<?> controller) {
        if (type != null && controller != null) {
            controllers.put(type.toUpperCase(), controller);
        }
    }

    /**
     * Retrieve a controller by type.
     */
    public IController<?> getController(String type) {
        return type == null ? null : controllers.get(type.toUpperCase());
    }

    /**
     * Initialize the registry with all known controllers.
     * Called at application startup.
     */
    public void loadControllers(BookingService bookingService) {
        ControllerFactory.createAndRegister(this, bookingService);
    }
}
