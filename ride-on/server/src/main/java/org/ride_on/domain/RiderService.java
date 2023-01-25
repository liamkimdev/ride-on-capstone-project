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
    public Result<Rider> createRider(Rider rider) {

        Result<Rider> result = new Result<>();

        if (rider == null) {
            result.addMessage(ActionStatus.INVALID, "a rider cannot be null");
            return result;
        }

        Trip trip = tripRepository.findByTripId(rider.getTripId());


        if (trip == null) {
            result.addMessage(ActionStatus.INVALID, "a trip cannot be null");
        }

        if (trip.getRiders().size() >= trip.getSeats()) {
            result.addMessage(ActionStatus.INVALID, "a rider could not join the trip, check again for available seats");
        }

        if (trip.getRiders().stream().anyMatch(r -> r.getUserId() == rider.getUserId())) {
            result.addMessage(ActionStatus.INVALID, "the rider already attached to this trip");
        }

        if (!result.isSuccess()) {
            return result;
        }

        Rider createRider = riderRepository.createRider(rider);
        result.setPayload(createRider);

        return result;
    }
}
