package algorithms;

import models.Seat;

import java.util.List;

public class CheapestSeatAlgo implements IAlgoBooking {
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