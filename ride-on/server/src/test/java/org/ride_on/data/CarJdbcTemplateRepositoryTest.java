package org.ride_on.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ride_on.models.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 5;

    @Autowired
    CarJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup(){
        knownGoodState.set();
    }

    @Test
    void shouldFindByCarId() {
        Car lamborghini = repository.findByCarId(1);
        assertEquals(1, lamborghini.getCarId());
        assertEquals("2022", lamborghini.getYear());
    }

    @Test
    void shouldCreateCar() {
        Car newCar = makeCar();
        Car actual = repository.createCar(newCar);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getCarId());
        assertEquals("Impala", actual.getModel());
    }


    @Test
    void shouldUpdateCar() {
        Car actual = repository.findByCarId(1);
        actual.setModel("Hurican");
        assertTrue(repository.updateCar(actual));
        assertEquals("Hurican", repository.findByCarId(1).getModel());
    }

    @Test
    void deleteByCarId() {
        assertTrue(repository.deleteByCarId(4));
        assertFalse(repository.deleteByCarId(4));
        assertFalse(repository.deleteByCarId(100000));
    }

    private Car makeCar() {
        Car newCar = new Car();
        newCar.setCarId(NEXT_ID);
        newCar.setInsurance(true);
        newCar.setRegistration(true);
        newCar.setMake("Chevy");
        newCar.setModel("Impala");
        newCar.setYear("2002");
        newCar.setColor("Gold");
        newCar.setLicensePlate("MT78978");
        newCar.setUserId(1);

        return newCar;
    }
}