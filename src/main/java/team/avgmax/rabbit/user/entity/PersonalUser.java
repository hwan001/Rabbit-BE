package team.avgmax.rabbit.user.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import team.avgmax.rabbit.global.util.UlidGenerator;
import team.avgmax.rabbit.user.entity.enums.Position;

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

    @Builder.Default
    @OneToMany(mappedBy = "personalUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserProvider> providers = new ArrayList<>();

    private String resume;

    @Enumerated(EnumType.STRING)
    private Position position;

    private BigDecimal carrot;

    private LocalDate birthdate;

    @Builder.Default
    @JoinColumn(name = "user_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sns> sns = new ArrayList<>();

    @Builder.Default
    @JoinColumn(name = "user_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skill = new ArrayList<>();

    @Builder.Default
    @JoinColumn(name = "user_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certification> certification = new ArrayList<>();

    @Builder.Default
    @JoinColumn(name = "user_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Career> career = new ArrayList<>();

    @Builder.Default
    @JoinColumn(name = "user_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> education = new ArrayList<>();

    // === 연관관계 편의 메서드 ===
    public void addProvider(UserProvider provider) {
        providers.add(provider);
        provider.setPersonalUser(this);
    }
}
