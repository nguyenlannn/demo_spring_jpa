package com.example.lan_demo.dto.res;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRes {
    private Integer id;
    private String email;
    private String name;
}
