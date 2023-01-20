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


    public RiderService(RiderRepository riderRepository) {
        this.riderRepository = riderRepository;
    }

    // joinTrip
    public Result<Trip> createRider(Rider rider, Trip trip) {
       Result<Trip> result = new Result<>();

       int currentSeat = trip.getSeats();
       trip.setSeats(currentSeat - 1); // updatedSeat

       Rider createRider = riderRepository.createRider(rider);

       if (createRider == null) {
           result.addMessage(ActionStatus.INVALID, "a rider cannot be null");
       }

       if (trip.getSeats() <= 0 ) {
           result.addMessage(ActionStatus.INVALID, "a rider could not join the trip, check again for available seats");
       }

       return result;
    }
}
