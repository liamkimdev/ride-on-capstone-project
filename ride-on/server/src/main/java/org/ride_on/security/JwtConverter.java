package org.ride_on.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.ride_on.models.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtConverter {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final String ISSUER = "ride-on";
    private final int EXPIRATION_MINUTES = 20160;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

    public String getTokenFromUser(AppUser user) {

        String authorities = user.getAuthorities().stream()
                .map(i -> i.getAuthority())
                .collect(Collectors.joining(","));

        // 3. Use JJWT classes to build a token.
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(user.getUsername())
                .claim("authorities", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }

    public AppUser getUserFromToken(String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {

            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.substring(7));

            String username = jws.getBody().getSubject();
            int appUserId = (int)jws.getBody().get("app_user_id");
            String authStr = (String) jws.getBody().get("authorities");

            List<SimpleGrantedAuthority> roles = Arrays.stream(authStr.split(","))
                    .map(r -> new SimpleGrantedAuthority(r))
                    .collect(Collectors.toList());

            return new AppUser(appUserId, username, null, true,
                    Arrays.asList(authStr.split(",")));

        } catch (JwtException e) {

            System.out.println(e);
        }

        return null;
    }
}
