package team.avgmax.rabbit.user.entity;

import team.avgmax.rabbit.global.entity.BaseTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.avgmax.rabbit.user.entity.enums.Role;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseTime {

    private String email;

    private String password;

    private String name;

    private String image;

    private Role role;
    
    private String phone;
}
