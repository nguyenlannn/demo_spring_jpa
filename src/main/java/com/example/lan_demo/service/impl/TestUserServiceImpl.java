package com.example.lan_demo.service.impl;

import com.example.lan_demo.dto.res.UserRes;
import com.example.lan_demo.produce.TestProduceDto;
import com.example.lan_demo.repository.UserTestRepository;
import com.example.lan_demo.service.UserTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Log4j2
public class TestUserServiceImpl implements UserTestService {

    private final UserTestRepository mUserTestRepository;

    @Override
    public List<UserRes> getListUserByName(String name) {
        List<TestProduceDto> userEntity = mUserTestRepository.findByName3(name);
        List<UserRes> res = new ArrayList<>();
        for (TestProduceDto i : userEntity) {
            UserRes userRes = new UserRes();
            userRes.setId(i.getId());
//            userRes.setName(i.getName());
            userRes.setEmail(i.getEmail());
            res.add(userRes);
        }
        return res;
    }
}
