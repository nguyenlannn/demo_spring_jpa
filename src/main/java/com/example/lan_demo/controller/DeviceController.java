package com.example.lan_demo.controller;

import com.example.lan_demo.base.BaseResponse;
import com.example.lan_demo.dto.req.ActiveDeviceReq;
import com.example.lan_demo.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping("/active")
    public BaseResponse activeDevice(ActiveDeviceReq activeDeviceReq) {
        deviceService.activeDevice(activeDeviceReq);
        return BaseResponse.success("Kích hoạt thiết bị thành công");
    }

    @PostMapping("/logOut")
    public BaseResponse logout( HttpServletRequest request) {
        deviceService.logout(request);
        return BaseResponse.success("Đăng xuất thành công");
    }
}
