package org.ride_on.data;

import org.ride_on.models.Car;
import org.springframework.transaction.annotation.Transactional;

public interface CarRepository {
    Car createCar(Car car);

    Car findByCarId(int carId);

    boolean updateCar(Car car);

    @Transactional
    boolean deleteByCarId(int carId);
}
