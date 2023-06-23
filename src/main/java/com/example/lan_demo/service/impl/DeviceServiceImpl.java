package com.example.lan_demo.service.impl;

import com.example.lan_demo.dto.req.DeleteDeviceReq;
import com.example.lan_demo.repository.DeviceRepository;
import com.example.lan_demo.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private final HttpServletRequest httpServletRequest;
    private final DeviceRepository deviceRepository;
    @Override
    public void logout( DeleteDeviceReq deleteDeviceReq) {
        Integer delete=deviceRepository.deleteByUserAgentAndAccessToken(deleteDeviceReq);
        if()
    }
}