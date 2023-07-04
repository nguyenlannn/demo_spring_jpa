package com.example.lan_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class Verification {
    private String code;
    private Timestamp updateTime;
    private Timestamp activationCodeLifetime;
}
