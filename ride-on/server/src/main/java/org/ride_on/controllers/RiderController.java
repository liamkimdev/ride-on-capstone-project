package org.ride_on.controllers;


import org.ride_on.domain.Result;
import org.ride_on.domain.RiderService;
import org.ride_on.domain.TripService;
import org.ride_on.models.Rider;
import org.ride_on.models.Trip;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/ride_on/rider")
public class RiderController {

    private final RiderService riderService;

    private final TripService tripService;

    public RiderController(RiderService riderService, TripService tripService) {
        this.riderService = riderService;
        this.tripService = tripService;
    }

    //createRider
    @PostMapping("/{userId}/{tripId}")
    public ResponseEntity<Object> createRider(@PathVariable int tripId, @RequestBody Rider rider) {
        Trip trip = tripService.findByTripId(tripId);
        Result<Trip> result = riderService.createRider(rider, trip);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }
}
