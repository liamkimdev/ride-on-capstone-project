package org.ride_on.data.mappers;

import org.ride_on.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setBankingAccount(rs.getString("banking_account"));
        user.setIdentification(rs.getString("identification"));
        user.setPreferences(rs.getString("preferences"));

        return user;
    }
}
