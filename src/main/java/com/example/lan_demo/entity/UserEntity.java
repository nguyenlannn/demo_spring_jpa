package com.example.lan_demo.entity;

import com.example.lan_demo.enums.UserEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.context.annotation.RequestScope;

import javax.persistence.*;
import javax.persistence.OneToMany;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, columnDefinition = "text")
    private String password;

    private UserEnum is_active;

    @OneToMany(mappedBy = "user")
    private Collection<DeviceEntity> devices;
}
