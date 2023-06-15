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
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserRes createAccount(UserReq userReq) {
        UserEntity userEntity= new UserEntity();
            if(mUserRepository.getMail(userReq.getEmail())){
            throw new BadRequestException("mail đã tồn tại");
        }
            userEntity.setMail(userReq.getEmail());
            userEntity.setPassword(passwordEncoder.encode(userReq.getPassword()));
            userEntity.setName(userReq.getName());
            mUserRepository.save(userEntity);
            UserRes userRes=new UserRes();
            userRes.setMail(userEntity.getMail());
            userRes.setName(userEntity.getName());
        return userRes;
    }
}
