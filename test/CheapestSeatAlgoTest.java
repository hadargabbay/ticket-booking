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
        // יצירת רשימה עם מחירים שונים - המטרה למצוא את ה-50.0
        List<Seat> seats = Arrays.asList(
                new Seat(1, 1, 200.0, true),
                new Seat(1, 2, 50.0, true),  // הכי זול
                new Seat(1, 3, 100.0, true)
        );

        Seat result = algo.findBestSeat(seats);

        assertNotNull(result);
        assertEquals(50.0, result.getPrice(), "האלגוריתם היה אמור לבחור במחיר 50.0");
    }

    @Test
    void testFindBestSeat_whenNoneAvailable() {
        CheapestSeatAlgo algo = new CheapestSeatAlgo();
        // מקרה קצה: יש מושב זול אבל הוא לא פנוי (false)
        List<Seat> seats = Arrays.asList(
                new Seat(1, 1, 50.0, false)
        );

        Seat result = algo.findBestSeat(seats);

        assertNull(result, "אם אין מושבים פנויים, התוצאה צריכה להיות null");
    }
}