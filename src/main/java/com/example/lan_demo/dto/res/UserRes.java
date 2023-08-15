package com.example.lan_demo.dto.res;

import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRes {
    private Integer id;
    private String email;
    private String name;
    private List<DeviceRes> devices;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        UserRes another = (UserRes) obj;
        return this.id.equals(another.id);
    }
}
