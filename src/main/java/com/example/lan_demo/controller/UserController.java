package com.example.lan_demo.controller;

import com.example.lan_demo.base.BaseResponse;
import com.example.lan_demo.config.UserDetailServiceConfig;
import com.example.lan_demo.dto.req.LoginReq;
import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.exception.BadRequestException;
import com.example.lan_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService mUserService;
    private final UserDetailServiceConfig mUserDetailServiceConfig;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> createAccount(@RequestBody @Valid UserReq userReq) {
        return ResponseEntity.ok().body(BaseResponse.success
                (mUserService.createAccount(userReq),"tạo tài khoản thành công"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq){
        UserDetails userDetails = mUserDetailServiceConfig.loadUserByUsername(loginReq.getEmail());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    loginReq.getPassword()));
        } catch (Exception e) {
            throw new BadRequestException("Mật khẩu không chính xác");
        }
        return ResponseEntity.ok(BaseResponse.success( "đăng nhập thành công"));
    }

//    @DeleteMapping("/logout")
//    public ResponseEntity<?> logout(){
//
//    }
}
