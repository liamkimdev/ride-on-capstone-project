package org.ride_on.domain;

import org.ride_on.data.RiderRepository;
import org.ride_on.data.TripRepository;
import org.ride_on.data.UserRepository;
import org.ride_on.models.Rider;
import org.ride_on.models.Trip;
import org.ride_on.models.User;

import java.util.List;

public class RiderService {

    private final TripRepository tripRepository;
    private final RiderRepository riderRepository;

    public RiderService(TripRepository tripRepository, RiderRepository riderRepository) {
        this.tripRepository = tripRepository;
        this.riderRepository = riderRepository;
    }

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public Trip findByTripId(int tripId) {
        return tripRepository.findByTripId(tripId);
    }

//    public Result<Trip> joinTrip(int riderId, int tripId) {
//       Result<Trip> result = new Result<>();
//       boolean updatedSeat = tripRepository.updateSeats(riderId);
//       boolean joinedTrip = riderRepository.joinTrip(tripId);
//
//       riderRepository.updatePayment(riderId);
//
//       if (!(updatedSeat && joinedTrip)) {
//           result.addMessage(ActionStatus.INVALID, "a rider could not join the trip, check again for available seats");
//       }
//
//       return result;
//    }

//    private Result<Trip> validate(Trip trip) {
//        Result<Trip> result = new Result<>();
//
//        if (trip == null) {
//            result.addMessage(ActionStatus.INVALID, "trip cannot be null");
//            return result;
//        }
//
//        if (Validations.isNullOrBlank(trip.getFirstName())) {
//            result.addMessage(ActionStatus.INVALID, "firstName is required");
//        }
//
//        if (Validations.isNullOrBlank(user.getLastName())) {
//            result.addMessage(ActionStatus.INVALID, "lastName is required");
//        }
//
//        if (Validations.isNullOrBlank(user.getBankingAccount())) {
//            result.addMessage(ActionStatus.INVALID, "bankingAccount is required");
//        }
//
//        if (Validations.isNullOrBlank(user.getIdentification())) {
//            result.addMessage(ActionStatus.INVALID, "identification is required");
//        }
//
//        return result;
    //return result;
   // }
}
