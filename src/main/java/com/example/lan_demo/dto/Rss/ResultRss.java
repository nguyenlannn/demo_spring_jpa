package com.example.lan_demo.dto.Rss;

import com.example.lan_demo.dto.res.DeviceRes;
import com.example.lan_demo.enums.DeviceEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultRss {
    private Integer id;
    private String email;
    private String name;
    private DeviceEnum isActive;
    private String userAgent;
    private String deviceVerification;
    private List<DeviceRes> devices;

    public ResultRss(Integer id, String email, String name, DeviceEnum isActive,String userAgent,String deviceVerification) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.isActive= isActive;
        this.userAgent=userAgent;
        this.deviceVerification=deviceVerification;
    }

}
