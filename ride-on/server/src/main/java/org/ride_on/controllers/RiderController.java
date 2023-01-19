package org.ride_on.controllers;


import org.ride_on.domain.RegisteredUserService;
import org.ride_on.domain.Result;
import org.ride_on.domain.RiderService;
import org.ride_on.models.Rider;
import org.ride_on.models.Trip;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/ride_on")
public class RiderController {

    private final RiderService service;

    public RiderController(RiderService service) {
        this.service = service;
    }


    //findAllTrips
        //should this @GetMapping link to unregistered userController @Getmapping??
    //findByTripId
    @GetMapping("/{userId}/{tripId}")
    public ResponseEntity<Trip> findByTripId(@PathVariable int tripId){
        Trip trip = service.findByTripId(tripId);
        if(trip == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(trip);
    }


    //createRider
    @PostMapping("/{userId}/{tripId}")
    public ResponseEntity<Object> createRider(@PathVariable int tripId, @RequestBody Rider rider) {
        Trip trip = service.findByTripId(tripId);
        Result<Trip> result = service.createRider(rider, trip);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }


}
