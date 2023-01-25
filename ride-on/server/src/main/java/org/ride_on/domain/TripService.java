package org.ride_on.domain;

import org.ride_on.data.TripRepository;
import org.ride_on.data.UserRepository;
import org.ride_on.models.Trip;
import org.ride_on.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {

    private final TripRepository repository;

    private final UserRepository userRepository;

    public TripService(TripRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Trip> findAll() {
        return repository.findAll();
    }

    public Trip findByTripId(int tripId) {
        return repository.findByTripId(tripId);
    }

    public Result<Trip> createTrip(Trip trip) {

        Result<Trip> result = validate(trip);
        if (!result.isSuccess()) {
            return result;
        }

        trip = repository.createTrip(trip);
        result.setPayload(trip);
        return result;
    }

    public boolean deleteByTripId(int tripId) {
        return repository.deleteByTripId(tripId);
    }

    public Result<Trip> updateByTripId(Trip trip, String username) {
        Result<Trip> result = validate(trip);
        if (!result.isSuccess()) {
            return result;
        }

        trip.setSeats(trip.getSeats() - 1);

        User user = userRepository.findByUsername(username);

        ArrayList<Trip> trips = user.getTrips();
        trips.add(trip);
        user.setTrips(trips);

        if (!repository.updateTrip(trip)) {
            String msg = String.format("tripId: %s, not found", trip.getTripId());
            result.addMessage(ActionStatus.NOT_FOUND, msg);
        }

        if (!userRepository.updateUser(user)) {
            String msg = String.format("An error occurred updating the userId: %", user.getUserId());
            result.addMessage(ActionStatus.NOT_FOUND, msg);
        }

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
