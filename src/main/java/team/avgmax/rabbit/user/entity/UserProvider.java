package team.avgmax.rabbit.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.avgmax.rabbit.user.entity.enums.ProviderType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProviderType providerType;

    @Column(nullable = false)
    private String providerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_user_id", nullable = false)
    private PersonalUser personalUser;

    // === 생성 메서드 ===
    public static UserProvider of(ProviderType providerType, String providerId) {
        UserProvider provider = new UserProvider();
        provider.providerType = providerType;
        provider.providerId = providerId;
        return provider;
    }

    // === 연관관계 메서드 ===
    public void setPersonalUser(PersonalUser personalUser) {
        this.personalUser = personalUser;
    }
}