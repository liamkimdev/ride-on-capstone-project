package org.ride_on.data;

import org.ride_on.data.mappers.RiderMapper;
import org.ride_on.data.mappers.TripMapper;
import org.ride_on.models.Rider;
import org.ride_on.models.Trip;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RiderJdbcTemplateRepository implements RiderRepository {

    private final JdbcTemplate jdbcTemplate;


    public RiderJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Rider> findByTripId(int tripId) {
        final String sql = "select * from rider where trip_id = ?;";
        List<Rider> riders = jdbcTemplate.query(sql, new RiderMapper(), tripId);

        return riders;
    }

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

    @Override
    // joinTrip
    public Rider createRider(Rider rider) {

        final String sql = "insert into rider (total_cost, payment_confirmation, user_id, trip_id) "
                + "values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBigDecimal(1, rider.getTotalCost());
            ps.setBoolean(2, rider.isPaymentConfirmation());
            ps.setInt(3, rider.getUserId());
            ps.setInt(4, rider.getTripId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        rider.setRiderId(keyHolder.getKey().intValue());
        return rider;
    }
}
