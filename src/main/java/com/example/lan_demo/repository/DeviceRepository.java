package com.example.lan_demo.repository;

import com.example.lan_demo.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    Boolean existsByUserAgentAndAccessToken(String userAgent, String accessToken);

    DeviceEntity findByUserAgentAndUserId(String userAgent, Integer id);

    DeviceEntity findByUserAgentAndAccessToken(String userAgent, String accessToken);

}
