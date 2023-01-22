package org.ride_on.domain;

import org.ride_on.data.CarRepository;
import org.ride_on.data.UserRepository;
import org.ride_on.models.Car;
import org.ride_on.models.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository repository;
    private final UserRepository userRepository;

    public CarService(CarRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Result<Car> createCar(Car car) {

        Result<Car> result = validate(car);
        if (!result.isSuccess()) {
            return result;
        }

        if (car.getCarId() != 0) {
            result.addMessage(ActionStatus.INVALID,"carId cannot be set for `add` operation");
        }


        //when you make a car you get more authority
        User user = userRepository.findByUserId(car.getUserId());
        user.setAuthorities(List.of(new SimpleGrantedAuthority("DRIVER")));
        userRepository.updateCredentials(user);

        car = repository.createCar(car);
        result.setPayload(car);
        return result;
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
