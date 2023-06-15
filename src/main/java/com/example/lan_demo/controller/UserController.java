package com.example.lan_demo.controller;

import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.dto.res.UserRes;
import com.example.lan_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {
//    private final UserService mUserService;
//
//    @PostMapping("/create")
//    public ResponseEntity<UserRes> createAccount(@RequestBody @Valid UserReq userReq) {
//        return ResponseEntity.ok().body(mUserService.createAccount(userReq));
//    }

}
