package org.ride_on.domain;

import org.junit.jupiter.api.Test;
import org.ride_on.data.CarRepository;
import org.ride_on.data.UserRepository;
import org.ride_on.models.Car;
import org.ride_on.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RegisteredUserServiceTest {

    @Autowired
    RegisteredUserService service;

    @MockBean
    CarRepository carRepository;

    @MockBean
    UserRepository userRepository;


    @Test
    public void shouldFindWithValidUserId() {
        // Arrange
        User expectedUser = new User(1, "lkim@dev-10.com", "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa", true, "Liam", "Kim", "01234abcde", "56789fghij", "drive is all about talking and listening", new ArrayList<>());

        when(userRepository.findByUsername("lkim@dev-10.com")).thenReturn(expectedUser);

        // Act
        UserDetails actualUser = service.loadUserByUsername("lkim@dev-10.com");

        // Assert
        assertEquals(expectedUser, actualUser);
    }


    @Test
    public void shouldCreateCarWithValidCar() {
        // Arrange
        Car car = new Car();
        car.setInsurance(true);
        car.setRegistration(true);
        car.setMake("Toyota");
        car.setModel("Camry");
        car.setColor("white");
        car.setLicensePlate("MT5123");

        Result<Car> expectedResult = new Result<>();
        expectedResult.setPayload(car);

        when(carRepository.createCar(car)).thenReturn(car);

        // Act
        Result<Car> actualResult = service.createCar(car);

        // Assert
        assertEquals(expectedResult.getPayload(), actualResult.getPayload());
        assertTrue(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateWithNullCar() {
        // Arrange
        Result<Car> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "car cannot be null");

        // Act
        Result<Car> actualResult = service.createCar(null);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateWithNoInsurance() {
        // Arrange
        Car car = new Car();
        car.setRegistration(true);
        car.setMake("Toyota");
        car.setModel("Camry");
        car.setColor("white");
        car.setLicensePlate("ABC123");

        Result<Car> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "insurance is required");

        // Act
        Result<Car> actualResult = service.createCar(car);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateWithNoRegistration() {
        // Arrange
        Car car = new Car();
        car.setInsurance(true);
        car.setMake("Toyota");
        car.setModel("Camry");
        car.setColor("white");
        car.setLicensePlate("ABC123");

        Result<Car> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "registration is required");

        // Act
        Result<Car> actualResult = service.createCar(car);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateWithNoMake() {
        // Arrange
        Car car = new Car();
        car.setInsurance(true);
        car.setRegistration(true);
        car.setModel("Camry");
        car.setColor("white");
        car.setLicensePlate("ABC123");

        Result<Car> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "car make is required");

        // Act
        Result<Car> actualResult = service.createCar(car);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateWithNoModel() {
        // Arrange
        Car car = new Car();
        car.setInsurance(true);
        car.setRegistration(true);
        car.setMake("Toyota");
        car.setColor("white");
        car.setLicensePlate("ABC123");

        Result<Car> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "car model is required");

        // Act
        Result<Car> actualResult = service.createCar(car);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateWithNoColor() {
        // Arrange
        Car car = new Car();
        car.setInsurance(true);
        car.setRegistration(true);
        car.setMake("Toyota");
        car.setModel("Camry");
        car.setLicensePlate("ABC123");

        Result<Car> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "car color is required");

        // Act
        Result<Car> actualResult = service.createCar(car);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }

    @Test
    public void shouldNotCreateWithNoLicensePlate() {
        // Arrange
        Car car = new Car();
        car.setInsurance(true);
        car.setRegistration(true);
        car.setMake("Toyota");
        car.setModel("Camry");
        car.setColor("white");

        Result<Car> expectedResult = new Result<>();
        expectedResult.addMessage(ActionStatus.INVALID, "license plate is required");

        // Act
        Result<Car> actualResult = service.createCar(car);

        // Assert
        assertEquals(expectedResult.getMessages(), actualResult.getMessages());
        assertFalse(actualResult.isSuccess());
    }
}