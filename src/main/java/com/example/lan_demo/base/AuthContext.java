package com.example.lan_demo.base;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
public class AuthContext {
    private Account account;

    public void setAuth(Account account) {
        this.account = account;
    }
}
