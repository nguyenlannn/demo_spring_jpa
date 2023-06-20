package com.example.lan_demo.service;

import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.dto.res.TokenRes;
import com.example.lan_demo.dto.res.UserRes;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

@Service
public interface UserService {
    UserRes createAccount(UserReq userReq);

    TokenRes refreshToken(HttpServletRequest httpServletRequest);
}
