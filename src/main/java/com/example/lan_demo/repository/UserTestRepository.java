package com.example.lan_demo.repository;

import com.example.lan_demo.entity.UserEntity;

import java.util.List;

public interface UserTestRepository {

    List<UserEntity> findAllByName1(String name);

    List<UserEntity> findAllByName2(String name);

    List<UserEntity> findAllByName3(String name);

    List<UserEntity> findAllByName4(String name);

    List<UserEntity> findAllByName5(String name);
}
