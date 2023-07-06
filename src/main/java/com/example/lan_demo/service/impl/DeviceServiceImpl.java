package com.example.lan_demo.service.impl;

import com.example.lan_demo.dto.Verification;
import com.example.lan_demo.dto.req.ActiveDeviceReq;
import com.example.lan_demo.entity.DeviceEntity;
import com.example.lan_demo.entity.UserEntity;
import com.example.lan_demo.enums.DeviceEnum;
import com.example.lan_demo.enums.UserEnum;
import com.example.lan_demo.exception.BadRequestException;
import com.example.lan_demo.repository.DeviceRepository;
import com.example.lan_demo.service.DeviceService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

import static com.example.lan_demo.enums.DeviceEnum.YES;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@RequiredArgsConstructor
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    private final HttpServletRequest httpServletRequest;

    @Override
    public void activeDevice(ActiveDeviceReq activeDeviceReq) {

        DeviceEntity deviceEntity = deviceRepository.findByUserAgentAndAccessToken(
                httpServletRequest.getHeader(USER_AGENT),
                httpServletRequest.getHeader(AUTHORIZATION).substring("Bearer ".length()));

        if(deviceEntity==null){
            throw new BadRequestException("Không tìm thấy thiết bị");
        }
        if (deviceEntity.getIsActive() == YES) {
            throw new BadRequestException("Thiết bị đã được kích hoạt");
        }

        Gson gson = new Gson();
        Verification target2 = gson.fromJson(deviceEntity.getDeviceVerification(), Verification.class);
        if (LocalDateTime.now().compareTo(LocalDateTime.parse(target2.getActivationCodeLifetime())) > 0) {
            throw new BadRequestException("Mã xác thực đã hết hạn");
        }

        if (!target2.getCode().equals(activeDeviceReq.getCode())) {
            throw new BadRequestException("Mã code không đúng");
        }
        deviceEntity.setIsActive(DeviceEnum.YES);
        deviceRepository.save(deviceEntity);
    }

    @Override
    public void logout(HttpServletRequest request) {

        DeviceEntity deviceEntity = deviceRepository.findByUserAgentAndAccessToken(//query vào db để tìm user agent và accesstoken
                request.getHeader(USER_AGENT),
                request.getHeader(AUTHORIZATION).substring("Bearer ".length()));

        if(deviceEntity==null){
            throw new BadRequestException("Đăng xuất thất bại");
        }
        deviceRepository.delete(deviceEntity);
    }
}