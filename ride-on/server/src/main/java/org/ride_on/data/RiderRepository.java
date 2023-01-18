package org.ride_on.data;

import org.ride_on.models.Rider;

public interface RiderRepository {
    Rider findByTripId(int tripId);

    boolean deleteByRiderId(int riderId);
}
