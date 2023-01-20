package org.ride_on.domain;

import org.ride_on.data.RiderRepository;
import org.ride_on.data.TripRepository;
import org.ride_on.models.Rider;
import org.ride_on.models.Trip;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiderService {
    private final RiderRepository riderRepository;
    private final TripRepository tripRepository;


    public RiderService(RiderRepository riderRepository, TripRepository tripRepository) {
        this.riderRepository = riderRepository;
        this.tripRepository = tripRepository;
    }

    public List<Rider> findRidersByTripId(int tripId) {
        return riderRepository.findRidersByTripId(tripId);
    }

    // joinTrip
    public Result<Trip> createRider(Rider rider) {

        Result<Trip> result = new Result<>();

        if (rider == null) {
            result.addMessage(ActionStatus.INVALID, "a rider cannot be null");
            return result;
        }

        Trip trip = tripRepository.findByTripId(rider.getTripId());

        int currentSeat = trip.getSeats();
        trip.setSeats(currentSeat - 1); // updatedSeat

        Rider createRider = riderRepository.createRider(rider);

        if (createRider == null) {
            result.addMessage(ActionStatus.INVALID, "a rider cannot be null");
        }

        if (trip.getSeats() <= 0) {
            result.addMessage(ActionStatus.INVALID, "a rider could not join the trip, check again for available seats");
        }

        return result;
    }
}
