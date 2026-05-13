package com.hit.ticketbookingclient.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/** A performance customers can book: metadata plus the list of seats in the hall. */
public class Show implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String description;
    private LocalDateTime showTime;
    private String venue;
    private List<Seat> seats;

    /** Empty constructor so JSON from the server can fill this object. */
    public Show() {
        // No-arg constructor for JSON deserialization (GSON)
    }

    /** Creates a fully described show including its seat layout. */
    public Show(int id, String title, String description, LocalDateTime showTime, String venue, List<Seat> seats) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.showTime = showTime;
        this.venue = venue;
        this.seats = seats;
    }

    /** Returns the internal numeric id of the show. */
    public int getId() {
        return id;
    }

    /** Returns the human-readable show title. */
    public String getTitle() {
        return title;
    }

    /** Returns extra text describing the event type or content. */
    public String getDescription() {
        return description;
    }

    /** Returns when the show starts. */
    public LocalDateTime getShowTime() {
        return showTime;
    }

    /** Returns where the show takes place. */
    public String getVenue() {
        return venue;
    }

    /** Returns all seats belonging to this show. */
    public List<Seat> getSeats() {
        return seats;
    }

    /** Updates the show id. */
    public void setId(int id) {
        this.id = id;
    }

    /** Updates the title. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Updates the description text. */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Updates the start date and time. */
    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    /** Updates the venue name. */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /** Replaces the whole seat list. */
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    /** Builds a short text summary useful for debugging. */
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

