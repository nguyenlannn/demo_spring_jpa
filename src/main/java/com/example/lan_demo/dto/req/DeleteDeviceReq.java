package com.example.lan_demo.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteDeviceReq {
    private String userAgent;
    private String accessToken;
}
