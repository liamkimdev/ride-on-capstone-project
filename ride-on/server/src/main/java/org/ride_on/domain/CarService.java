package org.ride_on.domain;

import org.ride_on.data.TripRepository;
import org.ride_on.models.Trip;
import org.ride_on.models.User;

import java.time.LocalDate;

public class CarService {

    private final TripRepository repository;

    public CarService(TripRepository repository) {
        this.repository = repository;
    }

    public Result<Trip> createTrip(Trip trip) {

        Result<Trip> result = validate(trip);
        if (!result.isSuccess()) {
            return result;
        }

        if (trip.getTripId() != 0) {
            result.addMessage(ActionStatus.INVALID,"tripId cannot be set for `add` operation");
        }

        trip = repository.createTrip(trip);
        result.setPayload(trip);
        return result;
    }

    private Result<Trip> validate(Trip trip) {
        Result<Trip> result = new Result<>();

        if (trip == null) {
            result.addMessage(ActionStatus.INVALID, "trip cannot be null");
            return result;
        }

        if (Validations.isNullOrBlank(trip.getDeparture())) {
            result.addMessage(ActionStatus.INVALID, "departure is required");
        }

        if (Validations.isNullOrBlank(trip.getArrival())) {
            result.addMessage(ActionStatus.INVALID, "arrival is required");
        }

        if (trip.getDate().isBefore(LocalDate.now())) {
            result.addMessage(ActionStatus.INVALID, "trip date must be in the future");
        }

        if (trip.getSeats() <= 0) {
            result.addMessage(ActionStatus.INVALID, "seat must be greater than 0");
        }

        if (trip.getCarId() <= 0) {
            result.addMessage(ActionStatus.INVALID, "carId must be exist");
        }

        return result;
    }
}
