package com.example.lan_demo.dto.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenRes {
    private String accessToken;
    private String refreshToken;
}
