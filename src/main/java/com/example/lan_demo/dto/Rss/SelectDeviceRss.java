package com.example.lan_demo.dto.Rss;

import com.example.lan_demo.entity.UserEntity;
import com.example.lan_demo.enums.DeviceEnum;

public interface SelectDeviceRss {
    Integer getId();
    DeviceEnum getIsActive();
    String getUserAgent();
    String getDeviceVerification();
    UserEntity getUserId();
}
