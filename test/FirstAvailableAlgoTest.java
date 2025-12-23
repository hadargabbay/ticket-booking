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
                new Seat(1, 1, 100.0, false), // תפוס
                new Seat(1, 2, 120.0, true),  // פנוי - זה מה שאמור לחזור
                new Seat(1, 3, 80.0, true)    // פנוי אבל לא ראשון
        );

        Seat result = algo.findBestSeat(seats);

        assertNotNull(result, "האלגוריתם היה אמור למצוא כיסא");
        assertEquals(2, result.getNumber(), "היה אמור לחזור כיסא מספר 2");
    }
}