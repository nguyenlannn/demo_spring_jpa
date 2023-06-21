package com.example.lan_demo.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository {

    Boolean existsByUserAgentAndAccessToken(String userAgent, String accessToken);
}
