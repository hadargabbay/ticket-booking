package server;

import com.google.gson.JsonElement;
import controllers.ControllerRegistry;
import controllers.IController;
import dto.*;
import json.JsonManager;
import models.Ticket;

/**
 * Dispatches incoming API requests to the appropriate controller
 * based on the action type.
 */
public class RequestDispatcher {
    private final ControllerRegistry controllerRegistry;
    private final JsonManager jsonManager;

    public RequestDispatcher(ControllerRegistry controllerRegistry) {
        this.controllerRegistry = controllerRegistry;
        this.jsonManager = JsonManager.getInstance();
    }

    /**
     * Process an ApiRequest and return an ApiResponse.
     */
    public ApiResponse dispatch(ApiRequest request) {
        if (request == null || request.getAction() == null) {
            return ApiResponse.error("Invalid request: missing action");
        }

        String action = request.getAction().toUpperCase();
        JsonElement payload = request.getPayload();

        try {
            switch (action) {
                case "BOOK_TICKET":
                    return handleBookTicket(payload);
                case "BOOK_BEST_SEAT":
                    return handleBookBestSeat(payload);
                default:
                    return ApiResponse.error("Unknown action: " + action);
            }
        } catch (Exception e) {
            return ApiResponse.error("Processing error: " + e.getMessage());
        }
    }

    private ApiResponse handleBookTicket(JsonElement payload) {
        if (payload == null) {
            return ApiResponse.error("Missing payload for BOOK_TICKET");
        }
        IController<Ticket> controller = (IController<Ticket>) controllerRegistry.getController("TICKET");
        if (controller == null) {
            return ApiResponse.error("Ticket controller not available");
        }
        Ticket ticket = jsonManager.getGson().fromJson(payload, Ticket.class);
        String result = controller.save(ticket);
        boolean success = result != null && result.startsWith("SUCCESS");
        return success ? ApiResponse.success(result) : ApiResponse.error(result);
    }

    private ApiResponse handleBookBestSeat(JsonElement payload) {
        if (payload == null) {
            return ApiResponse.error("Missing payload for BOOK_BEST_SEAT");
        }
        IController<Ticket> controller = (IController<Ticket>) controllerRegistry.getController("TICKET");
        if (controller == null) {
            return ApiResponse.error("Ticket controller not available");
        }
        BookBestSeatPayload p = jsonManager.getGson().fromJson(payload, BookBestSeatPayload.class);
        if (p.getShowTitle() == null || p.getShowTitle().isBlank()) {
            return ApiResponse.error("showTitle is required");
        }
        Ticket ticket = controller.bookBestSeat(p.getShowTitle(), p.getCustomerId());
        if (ticket == null) {
            return ApiResponse.error("Could not book best seat. Check show exists and has available seats.");
        }
        JsonElement data = jsonManager.getGson().toJsonTree(ticket);
        return ApiResponse.success("Best seat booked successfully", data);
    }
}
