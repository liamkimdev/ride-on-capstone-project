package org.ride_on.data;

import org.ride_on.data.mappers.CarMapper;
import org.ride_on.data.mappers.RiderMapper;
import org.ride_on.data.mappers.UserMapper;
import org.ride_on.models.User;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public User findByUsername(String username) {
        List<String> roles = getRolesByUsername(username);

        final String sql = "select * from user where username = ?; ";

        return jdbcTemplate.query(sql, new UserMapper(roles), username)
                .stream()
                .findFirst().orElse(null);
    }

    private List<String> getRolesByUsername(String username) {
        final String sql = "select r.name "
                + "from user_role ur "
                + "inner join app_role r on ur.app_role_id = r.app_role_id "
                + "inner join user u on ur.user_id = u.user_id "
                + "where u.username = ?";
        return jdbcTemplate.query(sql, (rs, rowId) -> rs.getString("name"), username);
    }

//    @Override
//    public User findByUserId(int userId, String username) {
//        List<String> roles = getRolesByUsername(username);
//
//        final String sql = "select * from user where user_id = ?;";
//
//        User user = jdbcTemplate.query(sql, new UserMapper(roles), userId).stream()
//                .findFirst().orElse(null);
//
//        if (user != null) {
//            addCars(user);
//            addRiders(user);
//        }
//
//        return user;
//    }

    @Override
    public User createUser(User user) {

        final String sql = "insert into user (username, password_hash,first_name, last_name, banking_account, identification, preferences) "
                + " values (?,?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getBankingAccount());
            ps.setString(6, user.getIdentification());
            ps.setString(7, user.getPreferences());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());

        updateRoles(user);

        return user;
    }
    private void updateRoles(User user) {
        // delete all roles, then re-add
        jdbcTemplate.update("delete from user_role where user_id = ?;", user.getUserId());

        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if (authorities == null) {
            return;
        }

        for (GrantedAuthority role : authorities) {
            String sql = "insert into user_role (user_id, app_role_id) "
                    + "select ?, app_role_id from app_role where `name` = ?;";
            jdbcTemplate.update(sql, user.getUserId(), role.getAuthority());
        }
    }

    @Transactional
    public void updateCredentials(User user) {

        final String sql = "update user set "
                + "username = ?, "
                + "enabled = ? "
                + "where user_id = ?";

        jdbcTemplate.update(sql,
                user.getUsername(), user.isEnabled(), user.getUserId());

        updateRoles(user);
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
