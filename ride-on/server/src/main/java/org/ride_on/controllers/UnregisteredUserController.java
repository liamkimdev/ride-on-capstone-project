package org.ride_on.controllers;

import org.apache.coyote.Response;
import org.ride_on.domain.Result;
import org.ride_on.domain.UnregisteredUserService;
import org.ride_on.models.Trip;
import org.ride_on.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/ride_on")
public class UnregisteredUserController {

    private final UnregisteredUserService service;

    public UnregisteredUserController(UnregisteredUserService service) {
        this.service = service;
    }

    // find all
    @GetMapping
    public List<Trip> findAll() {
        return service.findAll();
    }

    // create account
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody User user) {
        Result<User> result = service.createAccount(user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }
}
