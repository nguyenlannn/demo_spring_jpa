package com.example.lan_demo.repository;

import com.example.lan_demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);

    UserEntity findByEmail(String email);
}
