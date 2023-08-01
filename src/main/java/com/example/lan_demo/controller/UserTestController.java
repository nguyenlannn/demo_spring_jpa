package com.example.lan_demo.controller;

import com.example.lan_demo.base.BaseResponse;
import com.example.lan_demo.service.UserTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserTestController {

    private final UserTestService userTestService;

    @GetMapping("/getUserByEmail")
    public BaseResponse getUser(String name) {

        return BaseResponse.success(userTestService.getUserByEmail(name));
    }
}
