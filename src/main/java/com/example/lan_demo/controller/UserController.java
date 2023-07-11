package com.example.lan_demo.controller;

import com.example.lan_demo.base.BaseResponse;
import com.example.lan_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService mUserService;

    @GetMapping("/DetailUser")
    public BaseResponse getDetailUser(){
        return BaseResponse.success(mUserService.getDetailUser());
    }

}
