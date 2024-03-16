package com.nowoczesnyjunior.financialapp.usermodule.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nowoczesnyjunior.financialapp.openapi.model.TokenDto;
import com.nowoczesnyjunior.financialapp.usermodule.exception.UserCreationException;
import com.nowoczesnyjunior.financialapp.usermodule.model.AppUser;
import com.nowoczesnyjunior.financialapp.usermodule.model.UserRole;
import com.nowoczesnyjunior.financialapp.usermodule.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final long expirationTime;
    private final String secret;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository,
                       @Value("${jwt.expirationTime}") long expirationTime,
                       @Value("${jwt.secret}") String secret,
                       BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.expirationTime = expirationTime;
        this.secret = secret;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenDto authenticate(String username, String password) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        String tokenString = getTokenString(authenticate);
        return new TokenDto().token(tokenString);
    }

    @Transactional
    public void createNewUser(String username, String password, String repeatedPassword) {
        if (!password.equals(repeatedPassword)) {
            throw new IllegalArgumentException("The passwords do not match.");
        }
        if (userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("The given user name is already in the database");
        }
        try {
            saveNewUser(username, password);
        } catch (RuntimeException e) {
            throw new UserCreationException();
        }
    }
    private void saveNewUser(String username, String password) {
        userRepository.save(AppUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .enabled(true)
                .authorities(List.of(UserRole.ROLE_USER))
                .build());
    }

    private String getTokenString(Authentication authenticate) {
        UserDetails principal = (UserDetails) authenticate.getPrincipal();
        return JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));
    }
}
