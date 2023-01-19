package org.ride_on.data.mappers;

import org.ride_on.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserMapper implements RowMapper<User> {

    private final List<String> roles;

    public UserMapper(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        return new User(
            rs.getInt("user_id"),
            rs.getString("username"),
            rs.getString("password_hash"),
            rs.getBoolean("enabled"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("banking_account"),
            rs.getString("identification"),
            rs.getString("preferences"),
        roles);
    }
}
