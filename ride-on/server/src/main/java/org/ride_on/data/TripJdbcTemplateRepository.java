package org.ride_on.data;

import org.ride_on.data.mappers.RiderMapper;
import org.ride_on.data.mappers.TripMapper;
import org.ride_on.models.Trip;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TripJdbcTemplateRepository implements TripRepository {

    private final JdbcTemplate jdbcTemplate;

    public TripJdbcTemplateRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Trip> findAll(){
        final String sql = "select * from trip limit 1000;";
         return jdbcTemplate.query(sql, new TripMapper());
    }

    @Override
    public Trip findByTripId(int tripId){
        final String sql = "select * from trip where trip_id = ?;";
        Trip trip = jdbcTemplate.query(sql, new TripMapper(), tripId).stream().findFirst().orElse(null);

        if(trip != null){
            addRiders(trip);
        }

        return trip;
    }

    @Override
    public Trip createTrip(Trip trip){
        final String sql = "insert into trip (departure, arrival, seats, `date`, car_id) "
                + " values (?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, trip.getDeparture());
            ps.setString(2, trip.getArrival());
            ps.setInt(3, trip.getSeats());
            ps.setDate(4, Date.valueOf(trip.getDate()));
            ps.setInt(5, trip.getCarId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        trip.setTripId(keyHolder.getKey().intValue());
        return trip;
    }

    @Override
    public boolean updateTrip(Trip trip) {
        final String sql = "update trip set "
                + "departure = ?, "
                + "arrival = ?, "
                + "seats = ?, "
                + "`date` = ? "
                + "where trip_id = ?;";

        return jdbcTemplate.update(sql,
                trip.getDeparture(),
                trip.getArrival(),
                trip.getSeats(),
                trip.getDate(),
                trip.getTripId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteByTripId(int tripId) {
        // open
        jdbcTemplate.execute("set sql_safe_updates = 0;");
        // rider
        jdbcTemplate.update("delete from rider where trip_id = ?;", tripId);
        // trip
        boolean deleteTrip = jdbcTemplate.update("delete from trip where trip_id = ?;", tripId) > 0;
        // close
        jdbcTemplate.execute("set sql_safe_updates = 1;");

        return deleteTrip;
    }

    private void addRiders(Trip trip) {
        final String sql = "select * from rider where trip_id = ?;";
        var riders = jdbcTemplate.query(sql, new RiderMapper(), trip.getTripId());

        trip.setRiders(riders);
    }

}
