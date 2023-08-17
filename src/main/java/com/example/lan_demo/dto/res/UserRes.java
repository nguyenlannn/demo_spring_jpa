package com.example.lan_demo.dto.res;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRes {
    private Integer id;
    private String email;
    private String name;
    private List<DeviceRes> devices;
}
