package com.example.lan_demo.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.lan_demo.base.AuthContext;
import com.example.lan_demo.config.TokenConfig;
import com.example.lan_demo.dto.req.LoginReq;
import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.dto.res.TokenRes;
import com.example.lan_demo.dto.res.UserRes;
import com.example.lan_demo.entity.DeviceEntity;
import com.example.lan_demo.entity.UserEntity;
import com.example.lan_demo.exception.BadRequestException;
import com.example.lan_demo.exception.UnauthorizedException;
import com.example.lan_demo.repository.DeviceRepository;
import com.example.lan_demo.repository.UserRepository;
import com.example.lan_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

import static com.example.lan_demo.enums.DeviceEnum.NO;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository mUserRepository;
    private final PasswordEncoder mpasswordEncoder;
    private final AuthContext authContext;

    private final TokenConfig mTokenConfig;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final DeviceRepository mDeviceRepository;
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

    @Override
    public UserRes getDetailUser() {
        UserEntity userEntity = mUserRepository.findByEmail(authContext.getEmail());
        UserRes userRes = new UserRes();
        userRes.setId(userEntity.getId());
        userRes.setName(userEntity.getName());
        userRes.setEmail(userEntity.getEmail());
        return userRes;
    }

    @Override
    public TokenRes login(LoginReq loginReq, HttpServletRequest httpServletRequest) {
        try {
            daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                    loginReq.getEmail(),
                    loginReq.getPassword()));
        } catch (Exception e) {
            throw new BadRequestException("Mật khẩu không chính xác");
        }
        TokenRes tokenRes = mTokenConfig.generateToken(loginReq.getEmail(), httpServletRequest);

        UserEntity userEntity = mUserRepository.findByEmail(loginReq.getEmail());

            DeviceEntity deviceEntity = mDeviceRepository.findByUserAgentAndUserId(// tìm thiết bị có user agent và userid
                httpServletRequest.getHeader(USER_AGENT),
                userEntity.getId());

        if (Objects.nonNull(deviceEntity)) {// nếu tìm thấy =>set lại accesstoken và freshtoken
            deviceEntity.setAccessToken(tokenRes.getAccessToken());
            deviceEntity.setRefreshToken(tokenRes.getRefreshToken());

        } else {// không thấy thì tạo thiết bị mới
            deviceEntity = DeviceEntity.builder()
                    .userAgent(httpServletRequest.getHeader(USER_AGENT))
                    .accessToken(tokenRes.getAccessToken())
                    .refreshToken(tokenRes.getRefreshToken())
                    .isDelete(NO)
                    .user(userEntity)
                    .build();
        }
        mDeviceRepository.save(deviceEntity); //lưu vào csdl
        return tokenRes;
    }
}
