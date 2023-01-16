package org.ride_on.data.mappers;

import org.ride_on.models.Trip;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TripMapper implements RowMapper<Trip> {

    @Override
    public Trip mapRow(ResultSet rs, int i) throws SQLException {
        Trip trip = new Trip();
        trip.setTripId(rs.getInt("trip_id"));
        trip.setDeparture(rs.getString("departure"));
        trip.setArrival(rs.getString("arrival"));
        trip.setSeats(rs.getInt("seats"));
        trip.setDate(rs.getDate("date").toLocalDate());

        return trip;
    }
}
