package org.ride_on.controllers;


import org.ride_on.domain.Result;
import org.ride_on.domain.RiderService;
import org.ride_on.domain.TripService;
import org.ride_on.models.Rider;
import org.ride_on.models.Trip;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Find Rider(s)
    @GetMapping("/{tripId}")
    public ResponseEntity<Object> findRidersByTripId(@PathVariable int tripId) {
        List<Rider> riders = riderService.findRidersByTripId(tripId);

        return ResponseEntity.ok(riders);
    }


    //create Rider
    @PostMapping("/{userId}/{tripId}")
    public ResponseEntity<Object> createRider(@PathVariable int tripId, @PathVariable int userId, @RequestBody Rider rider) {
        if(rider.getTripId() != tripId && userId != rider.getUserId()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Rider> result = riderService.createRider(rider);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }
}
