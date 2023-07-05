package com.example.lan_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Verification {
    private String code;
    private String updateTime;
    private String activationCodeLifetime;
}
