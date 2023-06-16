package com.example.lan_demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements AuditorAware<String> {

    private final UserDetailServiceConfig mUserDetailServiceConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.empty();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mUserDetailServiceConfig).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
    }
}
