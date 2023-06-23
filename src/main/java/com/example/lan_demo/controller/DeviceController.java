package com.example.lan_demo.controller;

import com.example.lan_demo.base.BaseResponse;
import com.example.lan_demo.dto.req.DeleteDeviceReq;
import com.example.lan_demo.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/logout")
    public BaseResponse logout(@RequestBody DeleteDeviceReq deleteDeviceReq) {
        deviceService.logout(deleteDeviceReq);
        return BaseResponse.success("Đăng xuất thành công");
    }
}
