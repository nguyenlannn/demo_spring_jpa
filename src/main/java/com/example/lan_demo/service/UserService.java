package com.example.lan_demo.service;

import com.example.lan_demo.dto.req.ActiveReq;
import com.example.lan_demo.dto.req.LoginReq;
import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.dto.res.TokenRes;
import com.example.lan_demo.dto.res.UserRes;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface UserService {
    UserRes createAccount(UserReq userReq);

    void active(ActiveReq activeReq);

    TokenRes refreshToken(HttpServletRequest httpServletRequest);

    UserRes getDetailUser();

    TokenRes login(LoginReq loginReq, HttpServletRequest httpServletRequest);
}
