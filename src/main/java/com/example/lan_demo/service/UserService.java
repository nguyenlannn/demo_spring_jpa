package com.example.lan_demo.service;

import com.example.lan_demo.base.BaseListProduceDto;
import com.example.lan_demo.dto.req.ActiveReq;
import com.example.lan_demo.dto.req.LoginReq;
import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.dto.res.PageRes;
import com.example.lan_demo.dto.res.TokenRes;
import com.example.lan_demo.dto.res.UserRes;
import com.example.lan_demo.enums.UserEnum;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    UserRes createAccount(UserReq userReq);

    void active(ActiveReq activeReq);

    TokenRes refreshToken(HttpServletRequest httpServletRequest);

    UserRes getDetailUser();

    TokenRes login(LoginReq loginReq, HttpServletRequest httpServletRequest);

    List<UserRes> getListUser(String name);

    PageRes getPageUser(Long pageNo, Long pageSize, String name, String email, Integer id, UserEnum isActive);

    void testJoin();

    BaseListProduceDto<UserRes> getAllUser(Integer page, Integer size, String sorting);
}
