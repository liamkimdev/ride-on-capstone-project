package org.ride_on.data;

import org.ride_on.data.mappers.RiderMapper;
import org.ride_on.data.mappers.TripMapper;
import org.ride_on.models.Rider;
import org.ride_on.models.Trip;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RiderJdbcTemplateRepository implements RiderRepository {

    private final JdbcTemplate jdbcTemplate;


    public RiderJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Rider findByTripId(int tripId) {
        final String sql = "select * from rider where trip_id = ?;";
        Rider rider = jdbcTemplate.query(sql, new RiderMapper(), tripId).stream().findFirst().orElse(null);

        return rider;
    }


    // TODO: Repo or Service?
//    @Override
//    public boolean joinTrip(Rider rider, Trip trip) {

//        // if seats are greater than 0, rider can book the trip
//        // rider can be added
//        // decrease total seats by 1
//        // return true if rider is added
//        // else return false if seats were unavailable (0)
//
//        return false;
//    }

    @Override
    public boolean deleteByRiderId(int riderId) {
        // open
        jdbcTemplate.execute("set sql_safe_updates = 0;");
        // rider
        boolean deleteRider = jdbcTemplate.update("delete from rider where rider_id = ?;", riderId) > 0;
        // close
        jdbcTemplate.execute("set sql_safe_updates = 1;");

        return deleteRider;
    }
}
