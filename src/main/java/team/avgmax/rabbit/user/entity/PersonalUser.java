package team.avgmax.rabbit.user.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import team.avgmax.rabbit.global.util.UlidGenerator;
import team.avgmax.rabbit.user.entity.enums.Position;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalUser extends User {

    @Id
    @Column(name = "personal_user_id", length = 26, updatable = false, nullable = false)
    @Builder.Default
    private String id = UlidGenerator.generateMonotonic();
    
    private String google;

    private String kakao;

    private String naver;

    private String github;

    private String resume;

    @Enumerated(EnumType.STRING)
    private Position position;

    private BigDecimal carrot;

    private LocalDate birthdate;
}
