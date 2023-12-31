package com.example.lan_demo.dto.Rss;

import com.example.lan_demo.enums.DeviceEnum;

public interface SelectDeviceRss {
    Integer getId(); //tên cột ứng với tên cột trong database- bỏ get
    DeviceEnum getIsActive();
    String getUserAgent();
    String getDeviceVerification();
    Integer getUserId();
}
