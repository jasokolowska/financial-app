package com.nowoczesnyjunior.financialapp.usermodule.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nowoczesnyjunior.financialapp.usermodule.model.LoginCredentials;
import com.nowoczesnyjunior.financialapp.usermodule.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final long expirationTime;
    private final String secret;

    public LoginController(AuthenticationManager authenticationManager, @Value("${jwt.expirationTime}") long expirationTime,
                           @Value("${jwt.secret}") String secret) {
        this.authenticationManager = authenticationManager;
        this.expirationTime = expirationTime;
        this.secret = secret;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginCredentials credentials) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.username(),
                credentials.password()));

        UserDetails principal = (UserDetails) authenticate.getPrincipal();
        String tokenString = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));
        return new Token(tokenString);
    }
}
