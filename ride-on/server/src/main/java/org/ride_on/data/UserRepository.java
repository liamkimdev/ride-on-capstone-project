package org.ride_on.data;

import org.ride_on.models.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository {

  //  User findByUserId(int userId, String username);

    User createUser(User user);

    boolean updateUser(User user);

    @Transactional
    boolean deleteByUserId(int userId);

    User findByUsername(String username);
}
