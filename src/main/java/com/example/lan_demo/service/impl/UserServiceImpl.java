package com.example.lan_demo.service.impl;

import com.example.lan_demo.dto.req.UserReq;
import com.example.lan_demo.dto.res.UserRes;
import com.example.lan_demo.entity.UserEntity;
import com.example.lan_demo.exception.BadRequestException;
import com.example.lan_demo.repository.UserRepository;
import com.example.lan_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository mUserRepository;

    private final PasswordEncoder mpasswordEncoder;
    @Override
    public UserRes createAccount(UserReq userReq) {
        UserEntity userEntity = userReq.toUserEntity();

        if (mUserRepository.existsByEmail(userReq.getEmail())) {
            throw new BadRequestException("email đã tồn tại");
        }
        userEntity.setEmail(userReq.getEmail());
        userEntity.setPassword(mpasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setName(userReq.getName());
        mUserRepository.save(userEntity);
        UserRes userRes = new UserRes();
        userRes.setId(userEntity.getId());
        userRes.setEmail(userEntity.getEmail());
        userRes.setName(userEntity.getName());
        return userRes;
    }
}
