package com.example.lan_demo.service;

import com.example.lan_demo.dto.req.DeleteDeviceReq;
import org.springframework.stereotype.Service;

@Service
public interface DeviceService {

    void logout( DeleteDeviceReq deleteDeviceReq);
}
