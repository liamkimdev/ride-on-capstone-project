package org.ride_on.models;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private int carId;
    private boolean insurance;
    private boolean registration;
    private String make;
    private String model;
    private String year;
    private String color;
    private String licensePlate;
    private List<Trip> trips = new ArrayList<>();
    private int userId;

    public Car() {
    }

    public Car(int carId, boolean insurance, boolean registration, String make, String model, String year, String color, String licensePlate, List<Trip> trips, int userId) {
        this.carId = carId;
        this.insurance = insurance;
        this.registration = registration;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.licensePlate = licensePlate;
        this.trips = trips;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public boolean isRegistration() {
        return registration;
    }

    public void setRegistration(boolean registration) {
        this.registration = registration;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
