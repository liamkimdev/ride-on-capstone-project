package org.ride_on.controllers;

import org.apache.coyote.Response;
import org.ride_on.domain.TripService;
import org.ride_on.domain.Result;
import org.ride_on.models.Trip;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/ride_on/trip")
public class TripController {

    private final TripService service;

    public TripController(TripService service) {
        this.service = service;
    }

    @GetMapping
    public List<Trip> findAll() {
        return service.findAll();
    }

    //Find Trip
    @GetMapping("/{userId}/{tripId}")
    public ResponseEntity<Trip> findByTripId(@PathVariable int tripId){
        Trip trip = service.findByTripId(tripId);
        if(trip == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(trip);
    }

    // Create Trip
    @PostMapping
    public ResponseEntity<Object> createTrip(@RequestBody Trip trip){
        Result<Trip> result = service.createTrip(trip);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    // Delete Trip By Trip ID
    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteByTripId(@PathVariable int tripId) {
        if (service.deleteByTripId(tripId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
