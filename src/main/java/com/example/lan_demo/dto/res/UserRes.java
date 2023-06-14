package com.example.lan_demo.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRes {
    private String message;

    private Integer errorCode;
    private String mail;
    private String name;
}
