package com.example.lan_demo.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface DeviceService {

    void logout(HttpServletRequest request);
}
