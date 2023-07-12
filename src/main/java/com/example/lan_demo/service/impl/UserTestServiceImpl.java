package com.example.lan_demo.service.impl;

import com.example.lan_demo.dto.res.UserRes;
import com.example.lan_demo.repository.UserTestRepository;
import com.example.lan_demo.service.UserTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserTestServiceImpl implements UserTestService {

    private final UserTestRepository userTestRepository;

    @Override
    public UserRes getUserByEmail(String name) {

        return null;
    }
}
