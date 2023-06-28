package com.example.lan_demo.controller;

import com.example.lan_demo.base.BaseResponse;
import com.example.lan_demo.dto.req.ActiveReq;
import com.example.lan_demo.dto.req.LoginReq;
import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> createAccount(@RequestBody @Valid UserReq userReq) {
        return ResponseEntity.ok().body(BaseResponse.success
                (mUserService.createAccount(userReq),"tạo tài khoản thành công"));
    }

    @PostMapping("/active")
    public BaseResponse active(@RequestBody ActiveReq activeReq){
        mUserService.active(activeReq);
        return BaseResponse.success("Active tài khoản thành công");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq, HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(BaseResponse.success
                (mUserService.login(loginReq,httpServletRequest), "Đăng nhập thành công"));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<BaseResponse> refreshToken(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(BaseResponse.success(
                mUserService.refreshToken(httpServletRequest),
                "refresh token thành công"));
    }


}
