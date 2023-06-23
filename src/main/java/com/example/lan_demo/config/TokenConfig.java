package com.example.lan_demo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.lan_demo.dto.res.TokenRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenConfig {

    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    @Value("${JWT_ACCESS_TOKEN}")
    private Long JWT_ACCESS_TOKEN;

    @Value("${JWT_REFRESH_TOKEN}")
    private Long JWT_REFRESH_TOKEN;

    public TokenRes generateToken(String username, HttpServletRequest request) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        return TokenRes.builder()
                .accessToken(JWT.create()
                        .withSubject(username)
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("type", "access")
                        .sign(algorithm))
                .refreshToken(JWT.create()
                        .withSubject(username)
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("type", "refresh")
                        .sign(algorithm))
                .build();
    }
}
