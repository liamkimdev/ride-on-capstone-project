package org.ride_on.controllers;

import org.ride_on.domain.RegisteredUserService;
import org.ride_on.domain.Result;
import org.ride_on.models.Car;
import org.ride_on.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/ride_on")
public class RegisteredUserController {

    private final RegisteredUserService service;


    public RegisteredUserController(RegisteredUserService service) {
        this.service = service;
    }

    //find by user id
    @GetMapping("/{userId}")
    public ResponseEntity<User> findByUserId(@PathVariable int userId){
        User user = service.findByUserId(userId);
        if(user == null){;
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    //create car
    @PostMapping("/{userId}") //todo:  add path (/{userId/createCar)
    public ResponseEntity<Object> createCar(@RequestBody Car car){
        Result<Car> result = service.createCar(car);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }
}