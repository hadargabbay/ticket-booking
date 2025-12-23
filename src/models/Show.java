package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Show implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String title;
    private String description;
    private LocalDateTime showTime;
    private String venue;
    private List<Seat> seats;

    public Show(int id, String title, String description, LocalDateTime showTime, String venue, List<Seat> seats) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.showTime = showTime;
        this.venue = venue;
        this.seats = seats;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public String getVenue() {
        return venue;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", showTime=" + showTime +
                ", venue='" + venue + '\'' +
                ", seats=" + seats +
                '}';
    }
}

