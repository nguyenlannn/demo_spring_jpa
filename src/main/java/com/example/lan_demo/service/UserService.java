package com.example.lan_demo.service;

import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.dto.res.UserRes;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserRes createAccount(UserReq userReq);
}
