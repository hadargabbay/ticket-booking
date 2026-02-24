import org.junit.Test;
import static org.junit.Assert.*;
import service.BookingService;
import dao.DaoFileImpl;
import algorithms.CheapestSeatAlgo;
import models.Show;
import models.Ticket;
import java.util.List;

public class BookingServiceTest {

    @Test
    public void testBookingSystemIntegration() {
        DaoFileImpl dao = new DaoFileImpl("datasource.txt");
        CheapestSeatAlgo algo = new CheapestSeatAlgo();
        BookingService service = new BookingService(dao, algo);
        List<Show> allShows = dao.getAllShows();
        if (allShows != null && !allShows.isEmpty()) {
            Show firstShow = allShows.get(0);
            Ticket resultTicket = service.bookBestSeat(firstShow.getTitle(), 999);
            assertNotNull("The booking should return a valid Ticket object", resultTicket);
            assertEquals("The ticket should be for the correct show",
                    firstShow.getTitle(), resultTicket.getShow().getTitle());
        }
    }
}