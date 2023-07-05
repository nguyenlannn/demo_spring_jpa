package com.example.lan_demo.service;

import com.example.lan_demo.dto.req.ActiveDeviceReq;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface DeviceService {

    void logout(HttpServletRequest request);

    void activeDevice(ActiveDeviceReq activeDeviceReq);
}
