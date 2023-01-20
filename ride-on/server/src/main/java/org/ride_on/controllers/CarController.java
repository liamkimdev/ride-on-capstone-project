package org.ride_on.controllers;

import org.ride_on.domain.CarService;
import org.ride_on.domain.Result;
import org.ride_on.models.Car;
import org.ride_on.models.Trip;
import org.ride_on.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/ride_on/car")
public class CarController {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    //create car
    @PostMapping
    public ResponseEntity<Object> createCar(@RequestBody Car car, @AuthenticationPrincipal User user){
        if (user.getUserId() != car.getUserId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Result<Car> result = service.createCar(car);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }
}