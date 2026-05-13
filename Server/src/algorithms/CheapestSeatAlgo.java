package algorithms;

import models.Seat;

import java.util.List;

/** Picks the free seat with the lowest price. */
public class CheapestSeatAlgo implements IAlgoBooking {
    /**
     * Scans all seats and returns the cheapest one that is still marked available.
     */
    @Override
    public Seat findBestSeat(List<Seat> allSeats) {
        Seat cheapest = null;
        for (Seat seat : allSeats) {
            if (seat.isAvailable()) {
                if (cheapest == null || seat.getPrice() < cheapest.getPrice()) {
                    cheapest = seat;
                }
            }
        }
        return cheapest;
    }
}
