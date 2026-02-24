package server;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import controllers.ControllerRegistry;
import controllers.IController;
import controllers.TicketController;
import dto.*;
import json.JsonManager;
import models.Ticket;
import models.Show;
import java.util.List;

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
                case "GET_ALL_SHOWS":
                    return handleGetAllShows();
                case "ADD_SHOW":
                    return handleAddShow(payload);
                case "CANCEL_BOOKING":
                    return handleCancelBooking(payload);
                case "RESET_SHOW":
                    return handleResetShow(payload);
                case "DELETE_SHOW":
                    return handleDeleteShow(payload);
                default:
                    return ApiResponse.error("Unknown action: " + action);
            }
        } catch (Exception e) {
            return ApiResponse.error("Processing error: " + e.getMessage());
        }
    }

    private ApiResponse handleBookTicket(JsonElement payload) {
        if (payload == null) return ApiResponse.error("Missing payload for BOOK_TICKET");
        IController<Ticket> controller = (IController<Ticket>) controllerRegistry.getController("TICKET");
        if (controller == null) return ApiResponse.error("Ticket controller not available");
        Ticket ticket = jsonManager.getGson().fromJson(payload, Ticket.class);
        String result = controller.save(ticket);
        boolean success = result != null && result.startsWith("SUCCESS");
        return success ? ApiResponse.success(result) : ApiResponse.error(result);
    }
    // Processes seat booking requests
    private ApiResponse handleBookBestSeat(JsonElement payload) {
        if (payload == null) return ApiResponse.error("Missing payload for BOOK_BEST_SEAT");
        TicketController controller = (TicketController) controllerRegistry.getController("TICKET");
        if (controller == null) return ApiResponse.error("Ticket controller not available");
        BookBestSeatPayload p = jsonManager.getGson().fromJson(payload, BookBestSeatPayload.class);
        if (p.getShowTitle() == null || p.getShowTitle().isBlank()) {
            return ApiResponse.error("showTitle is required");
        }
        int amount = p.getAmount() > 0 ? p.getAmount() : 1;
        List<Ticket> tickets = controller.bookBestSeat(p.getShowTitle(), p.getCustomerName(), p.getAlgorithmPref(), amount);
        if (tickets == null || tickets.isEmpty()) {
            return ApiResponse.error("Could not book tickets. Check show exists and has enough available seats.");
        }
        JsonElement data = jsonManager.getGson().toJsonTree(tickets);
        return ApiResponse.success("Successfully booked " + tickets.size() + " tickets", data);
    }
    // show available shows
    private ApiResponse handleGetAllShows() {
        IController<Show> controller = (IController<Show>) controllerRegistry.getController("SHOW");
        if (controller == null) return ApiResponse.error("Show controller not available");
        List<Show> shows = controller.getAll();
        JsonElement data = jsonManager.getGson().toJsonTree(shows);
        return ApiResponse.success("Shows retrieved", data);
    }
    // Adds a new show to the system
    private ApiResponse handleAddShow(JsonElement payload) {
        if (payload == null) return ApiResponse.error("Missing payload for ADD_SHOW");
        IController<Show> controller = (IController<Show>) controllerRegistry.getController("SHOW");
        if (controller == null) return ApiResponse.error("Show controller not available");
        Show show = jsonManager.getGson().fromJson(payload, Show.class);
        String result = controller.save(show);
        boolean success = result != null && result.startsWith("SUCCESS");
        return success ? ApiResponse.success(result) : ApiResponse.error(result);
    }
    // when user cancle payment
    private ApiResponse handleCancelBooking(JsonElement payload) {
        if (payload == null || !payload.isJsonArray()) {
            return ApiResponse.error("Invalid payload for cancellation");
        }
        TicketController controller = (TicketController) controllerRegistry.getController("TICKET");
        if (controller == null) {
            return ApiResponse.error("Ticket controller not available");
        }
        JsonArray array = payload.getAsJsonArray();
        for (JsonElement el : array) {
            Ticket t = jsonManager.getGson().fromJson(el, Ticket.class);
            controller.cancelTicket(t);
        }
        return ApiResponse.success("Booking cancelled and seats released", null);
    }
    // Reset a show
    private ApiResponse handleResetShow(JsonElement payload) {
        if (payload == null) return ApiResponse.error("Missing show title");
        TicketController controller = (TicketController) controllerRegistry.getController("TICKET");
        if (controller == null) return ApiResponse.error("Ticket controller not available");
        String showTitle = payload.getAsString();
        controller.resetShow(showTitle);
        return ApiResponse.success("Show reset successfully", null);
    }
    // delete a show
    private ApiResponse handleDeleteShow(JsonElement payload) {
        if (payload == null) return ApiResponse.error("Missing show title");
        TicketController controller = (TicketController) controllerRegistry.getController("TICKET");
        if (controller == null) return ApiResponse.error("Ticket controller not available");
        String showTitle = payload.getAsString();
        controller.deleteShow(showTitle);
        return ApiResponse.success("Show deleted successfully", null);
    }
}