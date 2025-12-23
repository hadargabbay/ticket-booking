import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import models.Seat;
import algorithms.FirstAvailableAlgo;

class FirstAvailableAlgoTest {

    @Test
    void testFindBestSeat_returnsFirstAvailable() {
        FirstAvailableAlgo algo = new FirstAvailableAlgo();
        List<Seat> seats = Arrays.asList(
                new Seat(1, 1, 100.0, false), // occupied
                new Seat(1, 2, 120.0, true),  // available - that's what should return
                new Seat(1, 3, 80.0, true)    // available but not the first
        );

        Seat result = algo.findBestSeat(seats);

        assertNotNull(result, "the algorithm should find a seat");
        assertEquals(2, result.getNumber(), "the algorithm should return seat number 2");
    }

    @Test
    void testFindBestSeat_whenNoneAvailable() {
        FirstAvailableAlgo algo = new FirstAvailableAlgo();
        // edge case: all seats are occupied
        List<Seat> seats = Arrays.asList(
                new Seat(1, 1, 100.0, false),
                new Seat(1, 2, 120.0, false),
                new Seat(1, 3, 80.0, false)
        );

        Seat result = algo.findBestSeat(seats);

        assertNull(result, "if there are no available seats, the result should be null");
    }

    @Test
    void testFindBestSeat_withEmptyList() {
        FirstAvailableAlgo algo = new FirstAvailableAlgo();
        List<Seat> seats = Arrays.asList();

        Seat result = algo.findBestSeat(seats);

        assertNull(result, "if the list is empty, the result should be null");
    }
}