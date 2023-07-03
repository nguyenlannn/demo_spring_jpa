package com.example.lan_demo.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.lan_demo.base.AuthContext;
import com.example.lan_demo.config.TokenConfig;
import com.example.lan_demo.dto.Verification;
import com.example.lan_demo.dto.req.ActiveReq;
import com.example.lan_demo.dto.req.LoginReq;
import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.dto.res.TokenRes;
import com.example.lan_demo.dto.res.UserRes;
import com.example.lan_demo.entity.DeviceEntity;
import com.example.lan_demo.entity.UserEntity;
import com.example.lan_demo.enums.DeviceEnum;
import com.example.lan_demo.enums.UserEnum;
import com.example.lan_demo.exception.BadRequestException;
import com.example.lan_demo.exception.UnauthorizedException;
import com.example.lan_demo.repository.DeviceRepository;
import com.example.lan_demo.repository.UserRepository;
import com.example.lan_demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import static com.example.lan_demo.enums.UserEnum.YES;
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

    private final JavaMailSender mJavaMailSender;

    @Async// annotation luồng  bất đồng bộ
    @Override
    public UserRes createAccount(UserReq userReq) {
        UserEntity userEntity = userReq.toUserEntity();

        if (mUserRepository.existsByEmail(userReq.getEmail())) {//kiểm tra mail
            throw new BadRequestException("email đã tồn tại");
        }
        String random = RandomStringUtils.random(6, "1234567890");

        userEntity.setEmail(userReq.getEmail());
        userEntity.setPassword(mpasswordEncoder.encode(userEntity.getPassword()));//mã hóa mật khẩu
        userEntity.setName(userReq.getName());
        userEntity.setIsActive(UserEnum.NO);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();//convert dữ liệu và lưu vào db
        String json = null;
        try {
            json = ow.writeValueAsString(Verification.builder()
                            .code(random)
                            .updateTime(new Timestamp(System.currentTimeMillis()))
                    .build());
        } catch (JsonProcessingException ignored) {
        }
        userEntity.setVerification(json);

        sendEmailContainVerificationCode(userReq.getEmail(),random);

        mUserRepository.save(userEntity);
        UserRes userRes = new UserRes();
        userRes.setId(userEntity.getId());
        userRes.setEmail(userEntity.getEmail());
        userRes.setName(userEntity.getName());
        return userRes;
    }

    public void sendEmailContainVerificationCode(String toEmail, String code) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lannhatthuy@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Mã kích hoạt tài khoản của bạn(yêu cầu không cung cấp cho bất kì ai)!");
        message.setText(code);
        mJavaMailSender.send(message);
    }

    @Override
    public void active(ActiveReq activeReq) {
        UserEntity userEntity=mUserRepository.findByEmail(activeReq.getEmail());
        if(userEntity==null){
            throw new BadRequestException("email không tồn tại");
        }
        if(userEntity.getIsActive()==YES){
            throw new BadRequestException("tài khoản đã kích hoạt");
        }
        if(userEntity.getVerification().equals(activeReq.getCode())){
            throw new BadRequestException("mã code không đúng");
        }
        userEntity.setIsActive(YES);
        mUserRepository.save(userEntity);
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
        TokenRes tokenRes = mTokenConfig.generateToken(loginReq.getEmail(), httpServletRequest);//ren ra token

        UserEntity userEntity = mUserRepository.findByEmail(loginReq.getEmail());//lấy thông tin từ mail

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
                    .isDelete(DeviceEnum.NO)
                    .user(userEntity)
                    .build();
        }
        mDeviceRepository.save(deviceEntity); //lưu vào csdl
        return tokenRes;
    }
}
