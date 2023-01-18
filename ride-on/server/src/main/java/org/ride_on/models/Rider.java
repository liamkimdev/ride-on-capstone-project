package org.ride_on.models;

import java.math.BigDecimal;

public class Rider {
    private int riderId;
    private BigDecimal totalCost;
    private boolean paymentConfirmation = true;
    private int userId;
    private int tripId;

    // private Trip trip;

    public Rider() {
    }
    public Rider(int riderId, BigDecimal totalCost, boolean paymentConfirmation, int userId, int tripId) {
        this.riderId = riderId;
        this.totalCost = totalCost;
        this.paymentConfirmation = paymentConfirmation;
        this.userId = userId;
        this.tripId = tripId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getRiderId() {
        return riderId;
    }

    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isPaymentConfirmation() {
        return paymentConfirmation;
    }

    public void setPaymentConfirmation(boolean paymentConfirmation) {
        this.paymentConfirmation = paymentConfirmation;
    }
}

