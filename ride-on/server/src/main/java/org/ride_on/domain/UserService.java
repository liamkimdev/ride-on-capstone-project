package org.ride_on.domain;

import org.ride_on.data.UserRepository;
import org.ride_on.models.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public Result<User> createUser(User user) {

        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        user.setPassword(encoder.encode(user.getPassword()));

        user.setAuthorities(List.of(new SimpleGrantedAuthority("USER")));

        user = userRepository.createUser(user);
        result.setPayload(user);
        return result;
    }

    private Result<User> validate(User user) {
        Result<User> result = new Result<>();

        if (user == null) {
            result.addMessage(ActionStatus.INVALID, "user cannot be null");
            return result;
        }

        if (Validations.isNullOrBlank(user.getFirstName())) {
            result.addMessage(ActionStatus.INVALID, "firstName is required");
        }

        if (Validations.isNullOrBlank(user.getLastName())) {
            result.addMessage(ActionStatus.INVALID, "lastName is required");
        }

        if (Validations.isNullOrBlank(user.getBankingAccount())) {
            result.addMessage(ActionStatus.INVALID, "bankingAccount is required");
        }

        if (Validations.isNullOrBlank(user.getIdentification())) {
            result.addMessage(ActionStatus.INVALID, "identification is required");
        }

        return result;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null || !user.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return user;
    }

    public Result<User> create(String username, String password) {
        Result<User> result = validateUser(username, password);
        if (!result.isSuccess()) {
            return result;
        }

        password = encoder.encode(password);

        User user = new User(0, username, password, true, List.of("USER"));

        try {
            user = userRepository.createUser(user);
            result.setPayload(user);
        } catch (DuplicateKeyException e) {
            result.addMessage(ActionStatus.INVALID, "The provided username already exists");
        }

        return result;
    }

    private Result<User> validateUser(String username, String password) {
        Result<User> result = new Result<>();
        if (username == null || username.isBlank()) {
            result.addMessage(ActionStatus.INVALID, "username is required");
            return result;
        }

        if (password == null) {
            result.addMessage(ActionStatus.INVALID, "password is required");
            return result;
        }

        if (username.length() > 50) {
            result.addMessage(ActionStatus.INVALID, "username must be less than 50 characters");
        }

        if (!isValidPassword(password)) {
            result.addMessage(ActionStatus.INVALID,
                    "password must be at least 8 character and contain a digit," +
                            " a letter, and a non-digit/non-letter");
        }

        return result;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        return digits > 0 && letters > 0 && others > 0;
    }
}
