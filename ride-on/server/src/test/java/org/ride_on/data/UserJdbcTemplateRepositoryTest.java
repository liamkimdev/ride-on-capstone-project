package org.ride_on.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ride_on.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 3;

    @Autowired
    UserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindByUserId() {
        User matthew = repository.findByUserId(2);
        assertEquals(2, matthew.getUserId());
        assertEquals("Matthew", matthew.getFirstName());
        assertEquals("silence is life", matthew.getPreferences());
    }

    @Test
    void shouldCreateUser() {
        User user = new User(NEXT_ID,"Dwight", "Coddington", "abc123456", "ca56579", "rap music");
        User actual = repository.createUser(user);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getUserId());
        assertEquals("rap music", actual.getPreferences());
    }

    @Test
    void shouldUpdateUser() {
        User actual = repository.findByUserId(1);
        actual.setPreferences("pop music");
        actual.setBankingAccount("def75321");
        assertTrue(repository.updateUser(actual));

        assertEquals("pop music", repository.findByUserId(1).getPreferences());
    }

    @Test
    void deleteByUserId() {
        assertTrue(repository.deleteByUserId(1));
        assertFalse(repository.deleteByUserId(1));
        assertFalse(repository.deleteByUserId(100000));
    }
}