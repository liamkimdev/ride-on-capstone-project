package org.ride_on.data;

import org.ride_on.data.mappers.CarMapper;
import org.ride_on.data.mappers.RiderMapper;
import org.ride_on.data.mappers.UserMapper;
import org.ride_on.models.Trip;
import org.ride_on.models.User;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByUserId(int userId) {

        final String sql = "select * from user where user_id = ?;";

        User user = jdbcTemplate.query(sql, new UserMapper(), userId).stream()
                .findFirst().orElse(null);

        if (user != null) {
            addCars(user);
            addRiders(user);
        }

        return user;
    }

    @Override
    public User createUser(User user) {

        final String sql = "insert into user (first_name, last_name, banking_account, identification, preferences) "
                + " values (?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getBankingAccount());
            ps.setString(4, user.getIdentification());
            ps.setString(5, user.getPreferences());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public boolean updateUser(User user) {
        final String sql = "update user set "
                + "first_name = ?, "
                + "last_name = ?, "
                + "banking_account = ?, "
                + "identification = ?, "
                + "preferences = ? "
                + "where user_id = ?;";

        return jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getBankingAccount(),
                user.getIdentification(),
                user.getPreferences(),
                user.getUserId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteByUserId(int userId) {
        // open
        jdbcTemplate.execute("set sql_safe_updates = 0;");
        // rider
        jdbcTemplate.update("delete from rider where user_id = ?;", userId);
        // trip
        jdbcTemplate.update("delete from trip where car_id in (select car_id from car where user_id = ?);", userId);
        // car
        jdbcTemplate.update("delete from car where user_id = ?;", userId);
        // user
        boolean deleteUser = jdbcTemplate.update("delete from `user` where user_id = ?;", userId) > 0;
        // close
        jdbcTemplate.execute("set sql_safe_updates = 1;");

        return deleteUser;
    }

    private void addCars(User user) {

        final String sql = "select * from car where user_id = ?;";
        var cars = jdbcTemplate.query(sql, new CarMapper(), user.getUserId());
        user.setCars(cars);
    }

    private void addRiders(User user) {

        final String sql ="select * from rider where user_id = ?;";
        var riders = jdbcTemplate.query(sql, new RiderMapper(), user.getUserId());
        user.setRiders(riders);
    }
}
