package com.example.lan_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Verification {
    private String code;
    private LocalDateTime updateTime;
    private LocalDateTime activationCodeLifetime;
}
