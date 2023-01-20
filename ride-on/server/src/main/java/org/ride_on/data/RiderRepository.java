package org.ride_on.data;

import org.ride_on.models.Rider;
import org.ride_on.models.Trip;

import java.util.List;

public interface RiderRepository {
    List<Rider> findRidersByTripId(int tripId);

    boolean deleteByRiderId(int riderId);

    Rider createRider(Rider rider);

}
