package org.ride_on.domain;

import org.ride_on.data.CarRepository;
import org.ride_on.data.UserRepository;
import org.ride_on.models.Car;
import org.ride_on.models.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisteredUserService implements UserDetailsService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private List<UserDetails> users;

    public RegisteredUserService(CarRepository carRepository, UserRepository userRepository, PasswordEncoder encoder) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
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


    public Result<Car> createCar(Car car) {

        Result<Car> result = validate(car);
        if (!result.isSuccess()) {
            return result;
        }


        if (car.getCarId() != 0) {
            result.addMessage(ActionStatus.INVALID,"carId cannot be set for `add` operation");
        }

        car = carRepository.createCar(car);
        result.setPayload(car);
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

    private Result<Car> validate(Car car) {
        Result<Car> result = new Result<>();

        if (car == null) {
            result.addMessage(ActionStatus.INVALID, "car cannot be null");
            return result;
        }

        if (car.isInsurance() == false) {
            result.addMessage(ActionStatus.INVALID, "insurance is required");
        }

        if (car.isRegistration() == false) {
            result.addMessage(ActionStatus.INVALID, "registration is required");
        }

        if (car.getMake() == null) {
            result.addMessage(ActionStatus.INVALID, "car make is required");
        }

        if (car.getModel() == null) {
            result.addMessage(ActionStatus.INVALID, "car model is required");
        }

        if (car.getColor() == null) {
            result.addMessage(ActionStatus.INVALID, "car color is required");
        }
        if (car.getLicensePlate() == null) {
            result.addMessage(ActionStatus.INVALID, "license plate is required");
        }

        return result;
    }
}
