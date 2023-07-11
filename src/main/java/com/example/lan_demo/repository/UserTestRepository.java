package com.example.lan_demo.repository;

import com.example.lan_demo.entity.UserEntity;

import java.util.List;

public interface UserTestRepository {

    List<UserEntity> findByEmail1(String email);

    List<UserEntity> findByEmail2(String email);
}
