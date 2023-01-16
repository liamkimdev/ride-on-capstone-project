package org.ride_on.models;

import java.math.BigDecimal;

public class Rider {
    private int riderId;
    private BigDecimal totalCost;
    private boolean paymentConfirmation;

    public Rider() {
    }

    public Rider(int riderId, BigDecimal totalCost, boolean paymentConfirmation) {
        this.riderId = riderId;
        this.totalCost = totalCost;
        this.paymentConfirmation = paymentConfirmation;
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

