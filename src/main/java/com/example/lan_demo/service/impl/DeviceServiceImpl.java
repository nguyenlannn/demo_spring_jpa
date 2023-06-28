package com.example.lan_demo.service.impl;

import com.example.lan_demo.entity.DeviceEntity;
import com.example.lan_demo.exception.BadRequestException;
import com.example.lan_demo.repository.DeviceRepository;
import com.example.lan_demo.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@RequiredArgsConstructor
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    @Override
    public void logout(HttpServletRequest request) {

        DeviceEntity deviceEntity = deviceRepository.findByUserAgentAndAccessToken(
                request.getHeader(USER_AGENT),
                request.getHeader(AUTHORIZATION).substring("Bearer ".length()));

        if(deviceEntity==null){
                throw new BadRequestException("Đăng xuất thất bại");
        }
            deviceRepository.delete(deviceEntity);
    }
}