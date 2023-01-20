package org.ride_on.domain;

import org.junit.jupiter.api.Test;
import org.ride_on.data.UserRepository;
import org.ride_on.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;


    @Test
    void shouldNotBeAbleToCreateAccountWithNullUser() {
        User user = null;

        when(repository.createUser(user)).thenReturn(user);

        Result<User> result = service.createUser(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("user cannot be null", result.getMessages().get(0));
    }

    @Test
    public void shouldNotBeAbleToCreateAccountWithNullFirstName() {
        User user = new User(2,"Mheine@dev-10.com", "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa", true, null, "Heine", "1700144abcd", "mt1039876", "silence is life", new ArrayList<>());

        when(repository.createUser(user)).thenReturn(user);

        Result<User> result = service.createUser(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("firstName is required", result.getMessages().get(0));
    }

    @Test
    public void shouldNotBeAbleToCreateAccountWithNullLastName() {
        User user = new User(1,"lkim@dev-10.com", "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa", true, "Liam", null, "asojdjasoidjas", "56789fghij", "drive is all about talking and listening", new ArrayList<>());

        when(repository.createUser(user)).thenReturn(user);

        Result<User> result = service.createUser(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("lastName is required", result.getMessages().get(0));
    }

    @Test
    public void shouldNotBeAbleToCreateAccountWithNullBankingAccount() {
        User user = new User(1,"lkim@dev-10.com", "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa", true, "Liam", "Kim", null, "56789fghij", "drive is all about talking and listening", new ArrayList<>());

        when(repository.createUser(user)).thenReturn(user);

        Result<User> result = service.createUser(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("bankingAccount is required", result.getMessages().get(0));
    }

    @Test
    public void shouldNotBeAbleToCreateAccountWithEmptyBankingAccount() {
        User user = new User(1,"lkim@dev-10.com", "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa", true, "Liam", "Kim", "", "56789fghij", "drive is all about talking and listening", new ArrayList<>());

        when(repository.createUser(user)).thenReturn(user);

        Result<User> result = service.createUser(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("bankingAccount is required", result.getMessages().get(0));
    }

    @Test
    public void shouldNotBeAbleToCreateAccountWithNullIdentification() {
        User user = new User(1,"lkim@dev-10.com", "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa", true, "Liam", "Kim", "Mjkfh48923", null, "drive is all about talking and listening", new ArrayList<>());

        when(repository.createUser(user)).thenReturn(user);

        Result<User> result = service.createUser(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("identification is required", result.getMessages().get(0));
    }

    @Test
    public void shouldNotBeAbleToCreateAccountWithEmptyIdentification() {
        User user = new User(1,"lkim@dev-10.com", "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa", true, "Liam", "Kim", "djiasjasoijdsaoi", "", "drive is all about talking and listening", new ArrayList<>());

        when(repository.createUser(user)).thenReturn(user);


        Result<User> result = service.createUser(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ActionStatus.INVALID, result.getType());
        assertEquals("identification is required", result.getMessages().get(0));
    }

    @Test
    public void shouldCreateValidUser() {
        User user = new User(1,"lkim@dev-10.com", "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa", true, "Liam", "Kim", "jasiodjaiosj", "56789fghij", "drive is all about talking and listening", new ArrayList<>());

        when(repository.createUser(user)).thenReturn(user);

        Result<User> result = service.createUser(user);
        assertTrue(result.isSuccess());
        assertEquals(0, result.getMessages().size());
    }

    @Test
    public void shouldFindWithValidUserId() {
        // Arrange
        User expectedUser = new User(1, "lkim@dev-10.com", "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa", true, "Liam", "Kim", "01234abcde", "56789fghij", "drive is all about talking and listening", new ArrayList<>());

        when(repository.findByUsername("lkim@dev-10.com")).thenReturn(expectedUser);

        // Act
        UserDetails actualUser = service.loadUserByUsername("lkim@dev-10.com");

        // Assert
        assertEquals(expectedUser, actualUser);
    }
}