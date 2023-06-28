package com.example.lan_demo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.lan_demo.base.AuthContext;
import com.example.lan_demo.base.BaseResponse;
import com.example.lan_demo.repository.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class FilterConfig extends OncePerRequestFilter {
    @Value("${JWT_SECRET}")
    private String JWT_SECRET;
    private final AuthContext authContext;
    private final DeviceRepository mDeviceRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , FilterChain filterChain) throws ServletException, IOException {

        if (httpServletRequest.getServletPath().contains("basic")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
            httpServletResponse.setStatus(UNAUTHORIZED.value());

            String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);

            if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);

                    if (decodedJWT.getClaim("type").asString().equalsIgnoreCase("access")) {
                        String email = decodedJWT.getSubject();
                        authContext.setEmail(email);

                        if (mDeviceRepository.existsByUserAgentAndAccessToken(
                                httpServletRequest.getHeader(USER_AGENT),
                                token)) {
                            String userAgent=httpServletRequest.getHeader(USER_AGENT);
                            filterChain.doFilter(httpServletRequest, httpServletResponse);

                        } else {
                            new ObjectMapper().writeValue(
                                    httpServletResponse.getOutputStream()
                                    , BaseResponse.error("expired version", 401));
                        }

                    } else {
                        new ObjectMapper().writeValue(
                                httpServletResponse.getOutputStream()
                                , BaseResponse.error("unauthorized", 401));
                    }
                } catch (Exception exception) {
                    new ObjectMapper().writeValue(
                            httpServletResponse.getOutputStream()
                            , BaseResponse.error(exception.getMessage(), 401));
                }
            } else {
                new ObjectMapper().writeValue(
                        httpServletResponse.getOutputStream()
                        , BaseResponse.error("unauthorized", 401));
            }
        }
    }
}
