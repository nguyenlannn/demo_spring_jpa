package com.example.lan_demo.service;

import com.example.lan_demo.dto.req.ActiveReq;
import com.example.lan_demo.dto.req.LoginReq;
import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.dto.res.PageRes;
import com.example.lan_demo.dto.res.TokenRes;
import com.example.lan_demo.dto.res.UserRes;
import com.example.lan_demo.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    UserRes createAccount(UserReq userReq);

    void active(ActiveReq activeReq);

    TokenRes refreshToken(HttpServletRequest httpServletRequest);

    UserRes getDetailUser();

    TokenRes login(LoginReq loginReq, HttpServletRequest httpServletRequest);

    List<UserRes> getListUser(String name);

    PageRes getPageUserByName(Long pageNo,Long pageSize, String name);

    void testJoin();

    PageRes getPage(Long pageNo, Long pageSize);
}
