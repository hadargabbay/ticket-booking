package algorithms;

import models.Seat;
import java.util.List;

public interface IAlgoBooking {
    Seat findBestSeat(List<Seat> allSeats);
}