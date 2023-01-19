package org.ride_on.domain;

import org.junit.jupiter.api.Test;
import org.ride_on.data.TripRepository;
import org.ride_on.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CarServiceTest {
    @Autowired
    CarService service;

    @MockBean
    TripRepository tripRepository;

    @Test
    public void shouldCreateValidTrip() {
        // Arrange
        Trip trip = new Trip();
        trip.setDeparture("Seattle");
        trip.setArrival("Atlanta");
        trip.setDate(LocalDate.now().plusDays(1));
        trip.setSeats(4);
        trip.setCarId(1);

        Result<Trip> expectedResult = new Result<>();
        expectedResult.setPayload(trip);

        when(tripRepository.createTrip(trip)).thenReturn(trip);

        // Act
        Result<Trip> actualResult = service.createTrip(trip);

        // Assert
        assertEquals(expectedResult.getPayload(), actualResult.getPayload());
        assertTrue(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateNullTrip() {
        // Arrange
        Result<Trip> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "trip cannot be null");

        // Act
        Result<Trip> actualResult = service.createTrip(null);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateWithInvalidDeparture() {
        // Arrange
        Trip trip = new Trip();
        trip.setArrival("Atlanta");
        trip.setDate(LocalDate.now().plusDays(1));
        trip.setSeats(4);
        trip.setCarId(1);

        Result<Trip> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "departure is required");

        // Act
        Result<Trip> actualResult = service.createTrip(trip);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateWithInvalidArrival() {
        // Arrange
        Trip trip = new Trip();
        trip.setDeparture("Seattle");
        trip.setDate(LocalDate.now().plusDays(1));
        trip.setSeats(4);
        trip.setCarId(1);

        Result<Trip> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "arrival is required");

        // Act
        Result<Trip> actualResult = service.createTrip(trip);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateWithPastDate() {
        // Arrange
        Trip trip = new Trip();
        trip.setDeparture("Seattle");
        trip.setArrival("Atlanta");
        trip.setDate(LocalDate.now().minusDays(1));
        trip.setSeats(4);
        trip.setCarId(1);

        Result<Trip> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "trip date must be in the future");

        // Act
        Result<Trip> actualResult = service.createTrip(trip);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateWithZeroSeats() {
        // Arrange
        Trip trip = new Trip();
        trip.setDeparture("Seattle");
        trip.setArrival("Atlanta");
        trip.setDate(LocalDate.now().plusDays(1));
        trip.setSeats(0);
        trip.setCarId(1);

        Result<Trip> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "seat must be greater than 0");

        // Act
        Result<Trip> actualResult = service.createTrip(trip);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateWithInvalidCarId() {
        // Arrange
        Trip trip = new Trip();
        trip.setDeparture("Seattle");
        trip.setArrival("Atlanta");
        trip.setDate(LocalDate.now().plusDays(1));
        trip.setSeats(4);
        trip.setCarId(0);

        Result<Trip> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "carId must be exist");

        // Act
        Result<Trip> actualResult = service.createTrip(trip);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }
}