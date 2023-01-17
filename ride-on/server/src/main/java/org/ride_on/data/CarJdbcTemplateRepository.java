package org.ride_on.data;

import org.ride_on.data.mappers.CarMapper;
import org.ride_on.data.mappers.TripMapper;
import org.ride_on.models.Car;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class CarJdbcTemplateRepository implements CarRepository {

    private final JdbcTemplate jdbcTemplate;

    public CarJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Car createCar(Car car) {

        final String sql = "insert into car (insurance, registration, make, model, `year`, color, license_plate, user_id) "
                + " values (?,?,?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, car.isInsurance());
            ps.setBoolean(2, car.isRegistration());
            ps.setString(3, car.getMake());
            ps.setString(4, car.getModel());
            ps.setString(5, car.getYear());
            ps.setString(6, car.getColor());
            ps.setString(7, car.getLicensePlate());
            ps.setInt(8, car.getUserId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        car.setCarId(keyHolder.getKey().intValue());
        return car;
    }

    @Override
    public Car findByCarId(int carId) {

        final String sql = "select * from car where car_id  = ?;";

        Car car = jdbcTemplate.query(sql, new CarMapper(), carId).stream()
                .findFirst().orElse(null);

        if (car != null) {
            addTrips(car);
        }

        return car;
    }

    @Override
    public boolean updateCar(Car car) {
        final String sql = "update car set "
                + "insurance = ?, "
                + "registration = ?, "
                + "make = ?, "
                + "model = ?, "
                + "`year` = ?, "
                + "color = ?, "
                + "license_plate = ? "
                + "where car_id = ?;";

        return jdbcTemplate.update(sql,
                car.isInsurance(),
                car.isRegistration(),
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.getColor(),
                car.getLicensePlate(),
                car.getCarId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteByCarId(int carId) {
        // open
        jdbcTemplate.execute("set sql_safe_updates = 0;");
        // rider
        jdbcTemplate.update("delete from rider where trip_id in (select trip_id from trip where car_id = ?);", carId);
        // trip
        jdbcTemplate.update("delete from trip where car_id in (select car_id from car where car_id = ?);", carId);
        // car
        boolean deleteCar = jdbcTemplate.update("delete from car where car_id = ?;", carId) > 0;
        // close
        jdbcTemplate.execute("set sql_safe_updates = 1;");

        return deleteCar;
    }

    private void addTrips(Car car) {

        final String sql = "select * from trip where car_id = ?;";
        var trips = jdbcTemplate.query(sql, new TripMapper(), car.getCarId());
        car.setTrips(trips);
    }
}
