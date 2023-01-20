package org.ride_on.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ride_on.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TripJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 4;

    @Autowired
    TripJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findAll() {
        List<Trip> trips = repository.findAll();
        assertNotNull(trips);
        // if delete is first, down to 1
        // if add is first, may go as high as 4
        assertTrue(trips.size() >= 1 && trips.size() <= 4);
    }

    @Test
    void shouldFindByTripId() {
        Trip montana = repository.findByTripId(1);
        assertEquals(1, montana.getTripId());
        assertEquals("Missoula, MT", montana.getDeparture());
        assertEquals("2023-01-16", montana.getDate().toString());
    }

    @Test
    void shouldCreateTrip() {
        Trip trip = new Trip(NEXT_ID, "Macon, GA", "Warner Robins, GA", 4, new BigDecimal(20.00), LocalDate.of(2023,3,3), 2);
        Trip actual = repository.createTrip(trip);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getTripId());
        assertEquals("Warner Robins, GA", actual.getArrival());
    }

    @Test
    void updateTrip() {
        Trip actual = repository.findByTripId(1);
        actual.setDate(LocalDate.of(2023,3,4));
        // G-Wagon, 4 seats to 3 seats (1 seat taken)
        actual.setSeats(3);
        assertTrue(repository.updateTrip(actual));

        assertEquals(3, repository.findByTripId(1).getSeats());
    }

    @Test
    void deleteByTripId() {
        assertTrue(repository.deleteByTripId(2));
        assertFalse(repository.deleteByTripId(2));
        assertFalse(repository.deleteByTripId(100000));
    }
}