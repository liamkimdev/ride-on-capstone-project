package org.ride_on.models;

import java.time.LocalDate;

public class Trip {
    private int tripId;
    private String departure;
    private String arrival;
    private int seats;
    private LocalDate date;

    public Trip() {
    }

    public Trip(int tripId, String departure, String arrival, int seat, LocalDate date) {
        this.tripId = tripId;
        this.departure = departure;
        this.arrival = arrival;
        this.seats = seats;
        this.date = date;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}