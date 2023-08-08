package com.example.lan_demo.controller;

import com.example.lan_demo.base.BaseResponse;
import com.example.lan_demo.service.UserTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestUserController {

    private final UserTestService mUserTestService;

    @GetMapping("/test")
    public BaseResponse getListUserByName(@RequestParam String name) {
        return BaseResponse.success(mUserTestService.getListUserByName(name));
    }

}
