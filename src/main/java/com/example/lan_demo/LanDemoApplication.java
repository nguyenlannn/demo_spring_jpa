package com.example.lan_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync// cấu hình bất đồng bộ
public class LanDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LanDemoApplication.class, args);
    }

}
