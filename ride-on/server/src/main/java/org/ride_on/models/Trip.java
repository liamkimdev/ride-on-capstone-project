package org.ride_on.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Trip {
    private int tripId;
    private String departure;
    private String arrival;
    private int seats;
    private BigDecimal pricePerSeat;
    private LocalDate date;
    private int carId;
    private List<Rider> riders = new ArrayList<>();

    public Trip() {
    }

    public Trip(int tripId, String departure, String arrival, int seats, BigDecimal pricePerSeat, LocalDate date, int carId) {
        this.tripId = tripId;
        this.departure = departure;
        this.arrival = arrival;
        this.seats = seats;
        this.pricePerSeat = pricePerSeat;
        this.date = date;
        this.carId = carId;
    }

    public BigDecimal getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(BigDecimal pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }


    public List<Rider> getRiders() {
        return riders;
    }

    public void setRiders(List<Rider> riders) {
        this.riders = riders;
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