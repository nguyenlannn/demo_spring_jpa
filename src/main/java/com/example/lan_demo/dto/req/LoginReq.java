package com.example.lan_demo.dto.req;

import lombok.Data;

@Data
public class LoginReq {
    private String email;
    private String name;
    private String password;
}
