package algorithms;

import models.Seat;
import java.util.List;

/** Picks the first free seat in list order (usually lowest seat number first). */
public class FirstAvailableAlgo implements IAlgoBooking {
    /**
     * Returns the first seat in the list that is marked available, or null if the hall is full.
     */
    @Override
    public Seat findBestSeat(List<Seat> allSeats) {
        for (Seat seat : allSeats) {
            if (seat.isAvailable()) {
                return seat;
            }
        }
        return null;
    }
}
