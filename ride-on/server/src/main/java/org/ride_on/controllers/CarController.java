package org.ride_on.controllers;

import org.ride_on.domain.CarService;
import org.ride_on.domain.Result;
import org.ride_on.models.Car;
import org.ride_on.models.Trip;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/ride_on/car")
public class CarController {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    //createTrip
    @PostMapping
    public ResponseEntity<Object> createTrip(@RequestBody Trip trip){
        Result<Trip> result = service.createTrip(trip);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }
}