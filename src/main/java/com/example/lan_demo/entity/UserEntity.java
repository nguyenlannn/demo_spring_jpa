package com.example.lan_demo.entity;

import com.example.lan_demo.enums.UserEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.persistence.OneToMany;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Entity(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, columnDefinition = "text")
    private String password;

    private UserEnum isActive;

    private String verification;

    @OneToMany(mappedBy = "user")
    private Collection<DeviceEntity> devices;
}
