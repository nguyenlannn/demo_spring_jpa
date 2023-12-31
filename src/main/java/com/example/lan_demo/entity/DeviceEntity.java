package com.example.lan_demo.entity;

import com.example.lan_demo.enums.DeviceEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "device")
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, columnDefinition = "text")
    private String userAgent;
    @Column(nullable = false, columnDefinition = "text")
    private String accessToken;
    @Column(nullable = false, columnDefinition = "text")
    private String refreshToken;

    private DeviceEnum isDelete;

    private DeviceEnum isActive;

    private String deviceVerification;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
