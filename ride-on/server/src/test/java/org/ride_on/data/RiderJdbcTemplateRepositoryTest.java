package org.ride_on.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ride_on.models.Rider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RiderJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 4;

    @Autowired
    RiderJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindRidersByTripId() {
        List<Rider> riders = repository.findRidersByTripId(1);
        assertEquals(1, riders.size());
        assertEquals(true, riders.get(0).isPaymentConfirmation());
    }

    // TODO: Finish Testing for Rider After Working on Rider Classes (Vertical Approach)
//    @Test
//    void shouldCreateRider() {
//        Trip trip = tripRepository.findByTripId(2);
//
//        Rider rider = new Rider();
//        rider.setUserId(2);
//        rider.setTotalCost(trip.getPricePerSeat());
//        rider.setTripId(trip.getTripId());
//
//        Rider actual = riderRepository.createRider(rider);
//
//        assertNotNull(actual);
//        assertEquals(NEXT_ID, actual.getRiderId());
//        assertEquals(true, actual.isPaymentConfirmation());
//    }
//
//    @Test
//    void shouldDeleteByRiderId() {
//        assertTrue(riderRepository.deleteByRiderId(3));
//    }
}