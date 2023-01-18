package org.ride_on.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.ride_on.data.TripRepository;
import org.ride_on.data.UserRepository;
import org.ride_on.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UnregisteredUserServiceTest {

    @Autowired
    UnregisteredUserService service;

    @MockBean
    UserRepository userRepository;


    @Test
    void shouldNotBeAbleToCreateAccountWithNullUser() {
        User user = null;

        when(userRepository.createUser(user)).thenReturn(user);

        Result<User> result = service.createAccount(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("user cannot be null", result.getMessages().get(0));
    }

    @Test
    public void shouldNotBeAbleToCreateAccountWithNullFirstName() {
        User user = new User();
        user.setFirstName(null);
        user.setLastName("Heine");
        user.setBankingAccount("123456");
        user.setIdentification("123456789");

        when(userRepository.createUser(user)).thenReturn(user);

        Result<User> result = service.createAccount(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("firstName is required", result.getMessages().get(0));
    }

    @Test
    public void shouldNotBeAbleToCreateAccountWithNullLastName() {
        User user = new User();
        user.setFirstName("Liam");
        user.setLastName("");
        user.setBankingAccount("123456");
        user.setIdentification("123456789");

        when(userRepository.createUser(user)).thenReturn(user);

        Result<User> result = service.createAccount(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("lastName is required", result.getMessages().get(0));
    }

    @Test
    public void shouldNotBeAbleToCreateAccountWithNullBankingAccount() {
        User user = new User();
        user.setFirstName("Liam");
        user.setLastName("Kim");
        user.setBankingAccount(null);
        user.setIdentification("123456789");

        when(userRepository.createUser(user)).thenReturn(user);

        Result<User> result = service.createAccount(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("bankingAccount is required", result.getMessages().get(0));
    }

    @Test
    public void shouldNotBeAbleToCreateAccountWithEmptyBankingAccount() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBankingAccount("");
        user.setIdentification("123456789");

        when(userRepository.createUser(user)).thenReturn(user);

        Result<User> result = service.createAccount(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("bankingAccount is required", result.getMessages().get(0));
    }

    @Test
    public void shouldNotBeAbleToCreateAccountWithNullIdentification() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBankingAccount("123456");
        user.setIdentification(null);

        when(userRepository.createUser(user)).thenReturn(user);

        Result<User> result = service.createAccount(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("identification is required", result.getMessages().get(0));
    }

    @Test
    public void shouldNotBeAbleToCreateAccountWithEmptyEmptyIdentification() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBankingAccount("123456");
        user.setIdentification("");

        when(userRepository.createUser(user)).thenReturn(user);


        Result<User> result = service.createAccount(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("identification is required", result.getMessages().get(0));
    }

    @Test
    public void shouldCreateValidUserWithValidBankingAccount() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBankingAccount("123456");
        user.setIdentification("123456789");

        when(userRepository.createUser(user)).thenReturn(user);

        Result<User> result = service.createAccount(user);
        assertTrue(result.isSuccess());
        assertEquals(0, result.getMessages().size());
    }

    @Test
    public void shouldCreateValidUserWithValidIdentification() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBankingAccount("123456");
        user.setIdentification("123456789");

        when(userRepository.createUser(user)).thenReturn(user);

        Result<User> result = service.createAccount(user);
        assertTrue(result.isSuccess());
        assertEquals(0, result.getMessages().size());
    }

    @Test
    public void shouldCreateValidUser() {
        User user = new User();
        user.setFirstName("Liam");
        user.setLastName("Kim");
        user.setBankingAccount("123456");
        user.setIdentification("123456789");

        when(userRepository.createUser(user)).thenReturn(user);

        Result<User> result = service.createAccount(user);
        assertTrue(result.isSuccess());
        assertEquals(0, result.getMessages().size());
    }
}