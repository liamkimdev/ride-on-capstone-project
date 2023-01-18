package org.ride_on.data.mappers;

import org.ride_on.models.Rider;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RiderMapper implements RowMapper<Rider> {

    @Override
    public Rider mapRow(ResultSet resultSet, int i) throws SQLException {
        Rider rider = new Rider();
        rider.setRiderId(resultSet.getInt("rider_id"));
        rider.setTotalCost(resultSet.getBigDecimal("total_cost"));
        rider.setPaymentConfirmation(resultSet.getBoolean("payment_confirmation"));
        rider.setUserId(resultSet.getInt("user_id"));
        rider.setTripId(resultSet.getInt("trip_id"));

        return rider;
    }
}
