package com.example.lan_demo.dto.Rss;

import com.example.lan_demo.enums.DeviceEnum;

public interface SelectAllUserRss {
    Integer getId();
    String getEmail();
    String getName();
    Integer getDeviceId();
    DeviceEnum getIsActive();
    String getUserAgent();
    String getDeviceVerification();
    Integer getUserId();
}
