package org.ride_on.domain;

import org.junit.jupiter.api.Test;
import org.ride_on.data.RiderRepository;
import org.ride_on.models.Rider;
import org.ride_on.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RiderServiceTest {

    @Autowired
    RiderService service;

    @MockBean
    RiderRepository repository;

    @Test
    public void shouldCreateValidRider() {
        // Arrange
        Rider rider = new Rider();
        rider.setRiderId(1);

        Trip trip = new Trip();
        trip.setTripId(1);
        trip.setDeparture("Seattle");
        trip.setArrival("Atlanta");
        trip.setDate(LocalDate.now().plusDays(1));
        trip.setSeats(4);
        trip.setCarId(1);

        Result<Trip> expectedResult = new Result<>();

        when(repository.createRider(rider)).thenReturn(rider);

        // Act
        Result<Trip> actualResult = service.createRider(rider, trip);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertTrue(actualResult.isSuccess());
        assertEquals(3, trip.getSeats());
    }

    @Test
    public void shouldNotCreateRiderWithNullRider() {
        // Arrange
        Rider rider = null;
        Trip trip = new Trip();
        trip.setSeats(4);

        Result<Trip> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "a rider cannot be null");

        when(repository.createRider(rider)).thenReturn(null);

        // Act
        Result<Trip> actualResult = service.createRider(rider, trip);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateRiderWithNoAvailableSeats() {
        // Arrange
        Rider rider = new Rider();
        Trip trip = new Trip();
        trip.setSeats(0);

        Result<Trip> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "a rider could not join the trip, check again for available seats");

        when(repository.createRider(rider)).thenReturn(rider);

        // Act
        Result<Trip> actualResult = service.createRider(rider, trip);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }
}