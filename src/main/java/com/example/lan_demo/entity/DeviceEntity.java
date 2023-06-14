package com.example.lan_demo.entity;

import com.example.lan_demo.enums.DeviceEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEntity {
    private Integer id;
    private String userAgent;
    private String accessToken;
    private String refreshToken;
    private DeviceEnum isDelete;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
