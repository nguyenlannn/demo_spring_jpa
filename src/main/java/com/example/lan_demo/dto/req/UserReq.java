package com.example.lan_demo.dto.req;

import com.example.lan_demo.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReq {
    private  String email;
    private  String password;
    private  String name;

    public UserEntity toUserEntity() {
        return UserEntity.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }
}
