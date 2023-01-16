package org.ride_on.data;

import org.ride_on.models.Trip;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TripRepository {
    List<Trip> findAll();

    Trip findByTripId(int tripId);

    Trip createTrip(Trip trip);

    boolean updateTrip(Trip trip);

    @Transactional
    boolean deleteByTripId(int tripId);
}
