package org.ride_on.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    private final JwtConverter converter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtConverter converter, UserDetailsService userDetailsService) {
        this.converter = converter;
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authconfig) throws Exception {

        http.csrf().disable();

        http.cors();

        http.authorizeRequests()
                .antMatchers("/api/ride_on").permitAll()
                .antMatchers("/api/ride_on/user/authenticate").permitAll()
                .antMatchers("/refresh_token").authenticated()
                .antMatchers(HttpMethod.GET,
                        "/home").permitAll()
                // user
                .antMatchers(HttpMethod.GET,
                        "/api/ride_on/user/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST,
                        "/api/ride_on/user").permitAll()

                // trip
                .antMatchers(HttpMethod.GET,
                        "/api/ride_on/trip").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/ride_on/trip/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST,
                        "/api/ride_on/trip").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE,
                        "/api/ride_on/trip/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT,
                        "/api/ride_on/trip/**").hasAnyAuthority("USER", "ADMIN")

                // car
                .antMatchers(HttpMethod.POST,
                        "/api/ride_on/car").hasAnyAuthority("USER", "ADMIN")

                // rider
                .antMatchers(HttpMethod.GET,
                        "/api/ride_on/rider/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST,
                        "/api/ride_on/rider/**").hasAnyAuthority("USER", "ADMIN")

                // catch all
                .antMatchers(HttpMethod.GET,
                        "/api/ride_on/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST,
                        "/api/ride_on/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT,
                        "/api/ride_on/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/**").denyAll()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(authconfig), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}