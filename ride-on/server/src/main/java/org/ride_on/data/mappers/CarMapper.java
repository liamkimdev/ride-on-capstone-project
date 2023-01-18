package org.ride_on.data.mappers;

import org.ride_on.models.Car;
import org.ride_on.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet rs, int i) throws SQLException {
        Car car = new Car();
        car.setCarId(rs.getInt("car_id"));
        car.setInsurance(rs.getBoolean("insurance"));
        car.setRegistration(rs.getBoolean("registration"));
        car.setMake(rs.getString("make"));
        car.setModel(rs.getString("model"));
        car.setYear(rs.getString("year"));
        car.setColor(rs.getString("color"));
        car.setLicensePlate(rs.getString("license_plate"));
        car.setUserId(rs.getInt("user_id"));

        return car;
    }
}
