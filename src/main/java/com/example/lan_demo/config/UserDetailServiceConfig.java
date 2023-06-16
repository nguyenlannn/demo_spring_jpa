package com.example.lan_demo.config;

import com.example.lan_demo.entity.UserEntity;
import com.example.lan_demo.exception.BadRequestException;
import com.example.lan_demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDetailServiceConfig implements UserDetailsService {
    private final UserRepository mUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = mUserRepository.findByEmail(email);
        if (Objects.isNull(userEntity)) {
            throw new BadRequestException(email + " không tồn tại");
        }
        return new User(userEntity.getEmail(), userEntity.getPassword(),new ArrayList<>());
    }
}
