package org.ride_on.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private List<UserDetails> users;


    public AppUserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

        makeUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (userDetails == null) {
            throw new UsernameNotFoundException(username + " not found.");
        }

        return userDetails;
    }

    private void makeUsers() {

        UserDetails user = User.builder()
                .passwordEncoder(passwordEncoder::encode)
                .username("user")
                .password("password")
                .authorities("USER")
                .build();
        UserDetails admin = User.builder()
                .passwordEncoder(passwordEncoder::encode)
                .username("admin")
                .password("password")
                .authorities("ADMIN")
                .build();

        users = List.of(user, admin);
    }
}
