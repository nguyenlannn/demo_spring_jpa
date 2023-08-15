package com.example.lan_demo.dto.res;

import com.example.lan_demo.entity.UserEntity;
import com.example.lan_demo.enums.DeviceEnum;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeviceRes {
    private Integer id;
    private DeviceEnum isActive;
    private String userAgent;
    private String deviceVerification;
    private UserEntity userId;
}
