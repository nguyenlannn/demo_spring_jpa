package com.example.lan_demo.base;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
@RequiredArgsConstructor
public class AuthContext {
    private String email;
}
