package com.example.lan_demo.controller;

import com.example.lan_demo.base.BaseResponse;
import com.example.lan_demo.config.TokenConfig;
import com.example.lan_demo.config.UserDetailServiceConfig;
import com.example.lan_demo.dto.req.LoginReq;
import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.dto.res.TokenRes;
import com.example.lan_demo.exception.BadRequestException;
import com.example.lan_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/basic")
public class BasicController {
    private final UserService mUserService;
    private final UserDetailServiceConfig mUserDetailServiceConfig;
    private final AuthenticationManager authenticationManager;

    private final TokenConfig mTokenConfig;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> createAccount(@RequestBody @Valid UserReq userReq) {
        return ResponseEntity.ok().body(BaseResponse.success
                (mUserService.createAccount(userReq),"tạo tài khoản thành công"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq, HttpServletRequest httpServletRequest){
        UserDetails userDetails = mUserDetailServiceConfig.loadUserByUsername(loginReq.getEmail());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    loginReq.getPassword()));
        } catch (Exception e) {
            throw new BadRequestException("Mật khẩu không chính xác");
        }
        TokenRes tokenRes=mTokenConfig.generateToken(userDetails, httpServletRequest);
        return ResponseEntity.ok(BaseResponse.success(tokenRes, "Đăng nhập thành công"));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<BaseResponse> refreshToken(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(BaseResponse.success(
                mUserService.refreshToken(httpServletRequest),
                "refresh token thành công"));
    }
}
