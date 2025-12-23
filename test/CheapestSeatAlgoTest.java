import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import models.Seat;
import algorithms.CheapestSeatAlgo;

class CheapestSeatAlgoTest {

    @Test
    void testFindBestSeat_returnsCheapest() {
        CheapestSeatAlgo algo = new CheapestSeatAlgo();
        // create a list with different prices - the goal is to find the 50.0
        List<Seat> seats = Arrays.asList(
                new Seat(1, 1, 200.0, true),
                new Seat(1, 2, 50.0, true),  // the cheapest
                new Seat(1, 3, 100.0, true)
        );

        Seat result = algo.findBestSeat(seats);

        assertNotNull(result);
        assertEquals(50.0, result.getPrice(), "the algorithm should return the cheapest seat price 50.0");
    }

    @Test
    void testFindBestSeat_whenNoneAvailable() {
        CheapestSeatAlgo algo = new CheapestSeatAlgo();
        // edge case: there is a cheap seat but it is not available (false)
        List<Seat> seats = Arrays.asList(
                new Seat(1, 1, 50.0, false)
        );

        Seat result = algo.findBestSeat(seats);

        assertNull(result, "if there are no available seats, the result should be null");
    }
}