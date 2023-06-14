package com.example.lan_demo.service;

import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.dto.res.UserRes;

public interface UserService {
    UserRes createAccount(UserReq userReq);
}
