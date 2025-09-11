package team.avgmax.rabbit.user.entity;

import team.avgmax.rabbit.global.entity.BaseTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import lombok.NoArgsConstructor;
import team.avgmax.rabbit.user.entity.enums.Role;

@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class User extends BaseTime {
    private String email;
    private String password;
    private String name;
    private String image;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String phone;

    public void updateRoleToBunny() {
        this.role = Role.ROLE_BUNNY;
    }

    protected void changeName(String name) {
        this.name = name;
    }

    protected void changeImage(String image) {
        this.image = image;
    }
    
    protected void changeEmail(String email) {
        this.email = email;
    }
}