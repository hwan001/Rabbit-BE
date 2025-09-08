package team.avgmax.rabbit.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import team.avgmax.rabbit.global.util.UlidGenerator;
import team.avgmax.rabbit.user.entity.enums.Position;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalUser extends User {

    @Id
    @Column(name = "personal_user_id", length = 26, updatable = false, nullable = false)
    @Builder.Default
    private String id = UlidGenerator.generateMonotonic();

    @OneToMany(mappedBy = "personalUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserProvider> providers = new ArrayList<>();

    private String resume;

    @Enumerated(EnumType.STRING)
    private Position position;

    private BigDecimal carrot;

    private LocalDate birthdate;

    // === 연관관계 편의 메서드 ===
    public void addProvider(UserProvider provider) {
        providers.add(provider);
        provider.setPersonalUser(this);
    }
}