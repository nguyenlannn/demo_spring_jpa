package com.example.lan_demo.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.dto.res.TokenRes;
import com.example.lan_demo.dto.res.UserRes;
import com.example.lan_demo.entity.UserEntity;
import com.example.lan_demo.exception.BadRequestException;
import com.example.lan_demo.exception.UnauthorizedException;
import com.example.lan_demo.repository.UserRepository;
import com.example.lan_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository mUserRepository;

    private final PasswordEncoder mpasswordEncoder;

    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    @Value("${JWT_ACCESS_TOKEN}")
    private Long JWT_ACCESS_TOKEN;
    @Override
    public UserRes createAccount(UserReq userReq) {
        UserEntity userEntity = userReq.toUserEntity();

        if (mUserRepository.existsByEmail(userReq.getEmail())) {
            throw new BadRequestException("email đã tồn tại");
        }
        userEntity.setEmail(userReq.getEmail());
        userEntity.setPassword(mpasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setName(userReq.getName());
        mUserRepository.save(userEntity);
        UserRes userRes = new UserRes();
        userRes.setId(userEntity.getId());
        userRes.setEmail(userEntity.getEmail());
        userRes.setName(userEntity.getName());
        return userRes;
    }

    @Override
    public TokenRes refreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);//lấy ra header của request

        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();//
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                if (decodedJWT.getClaim("type").asString().equalsIgnoreCase("refresh")) {
                    UserEntity userEntity = mUserRepository.findByEmail(decodedJWT.getSubject());
                    return TokenRes.builder()
                            .accessToken(JWT.create()
                                    .withSubject(userEntity.getEmail())
                                    .withExpiresAt(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN))
                                    .withIssuer(request.getRequestURL().toString())
                                    .withClaim("type", "access")
                                    .sign(algorithm))
                            .refreshToken(refreshToken)
                            .build();
                    //updateAccessToken(request, tokenProduceDto);
                } else {
                    throw new UnauthorizedException("unauthorized");
                }
            } catch (Exception exception) {
                throw new UnauthorizedException(exception.getMessage());
            }
        } else {
            throw new UnauthorizedException("unauthorized");
        }
    }
}
