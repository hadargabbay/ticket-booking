import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import service.BookingService;
import dao.DaoFileImpl;
import algorithms.CheapestSeatAlgo;
import models.Show;
import models.Ticket;
import java.util.List;

public class BookingServiceTest {

    // Smoke test: if real data exists, tries one booking and checks the ticket points at the same show.
    @Test
    public void testBookingSystemIntegration() {
        DaoFileImpl dao = new DaoFileImpl("datasource.txt");
        CheapestSeatAlgo algo = new CheapestSeatAlgo();
        BookingService service = new BookingService(dao, algo);
        List<Show> allShows = dao.getAllShows();
        assumeTrue(allShows != null && !allShows.isEmpty());
        Show firstShow = allShows.get(0);
        List<Ticket> booked = service.bookBestSeat(firstShow.getTitle(), 1, "CHEAPEST", 1);
        assumeTrue(booked != null && !booked.isEmpty());
        Ticket resultTicket = booked.get(0);
        assertNotNull("The booking should return a valid Ticket object", resultTicket);
        assertEquals("The ticket should be for the correct show",
                firstShow.getTitle(), resultTicket.getShow().getTitle());
    }
}