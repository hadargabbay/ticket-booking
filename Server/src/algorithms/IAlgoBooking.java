package algorithms;

import models.Seat;
import java.util.List;

// Strategy interface: picks which free seat to offer next during booking.
public interface IAlgoBooking {
    // Chooses one available seat from the list, or returns null if none are free.
    Seat findBestSeat(List<Seat> allSeats);
}
