package com.example.lan_demo.service;

import com.example.lan_demo.dto.res.UserRes;
import org.springframework.stereotype.Service;

@Service
public interface UserTestService {
    UserRes getUserByEmail();
}
