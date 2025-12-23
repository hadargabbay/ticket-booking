package algorithms;

import models.Seat;
import java.util.List;

public class FirstAvailableAlgo implements IAlgoBooking {
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