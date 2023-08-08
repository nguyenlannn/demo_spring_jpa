package com.example.lan_demo.service;

import com.example.lan_demo.dto.res.UserRes;
import java.util.List;

public interface UserTestService {
    List<UserRes> getListUserByName(String name);
}
