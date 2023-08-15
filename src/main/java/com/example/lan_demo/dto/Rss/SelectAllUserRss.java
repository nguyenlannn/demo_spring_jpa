package com.example.lan_demo.dto.Rss;

import com.example.lan_demo.dto.res.DeviceRes;

import java.util.Set;

public interface SelectAllUserRss {
    Integer getId();
    String getEmail();
    String getName();
    Set<DeviceRes> getDevices();
}
