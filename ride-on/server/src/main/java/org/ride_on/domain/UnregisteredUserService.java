package org.ride_on.domain;

import org.ride_on.data.TripRepository;
import org.ride_on.data.UserRepository;
import org.ride_on.models.Trip;
import org.ride_on.models.User;

import java.util.List;

public class UnregisteredUserService {
    private final TripRepository tripRepository;

    private final UserRepository userRepository;

    public UnregisteredUserService(TripRepository tripRepository, UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public Result<User> createAccount(User user) {

        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != 0) {
            result.addMessage(ActionStatus.INVALID, "userId cannot be set for `add` operation");
            return result;
        }

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
}
