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
import team.avgmax.rabbit.user.exception.UserException;
import team.avgmax.rabbit.user.exception.UserError;

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

    private String portfolio;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Builder.Default
    @Column(precision = 20)
    private BigDecimal carrot = BigDecimal.valueOf(50_000_000);

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
    private List<Education> educations = new ArrayList<>();

    // === 연관관계 편의 메서드 ===
    public void updateBasicInfo(String name, LocalDate birthdate, String image, String resume, Position position) {
        this.changeName(name);
        this.changeImage(image);
        this.birthdate = birthdate;
        this.resume = resume;
        this.position = position;   
    } 

    public void addProvider(UserProvider provider) {
        providers.add(provider);
        provider.setPersonalUser(this);
    }

    public void addEducation(Education education){
        educations.add(education);
    }

    public void addCarrot(BigDecimal carrot) {
        this.carrot = this.carrot.add(carrot);
    }

    public void subtractCarrot(BigDecimal carrot) {
        if (this.carrot.compareTo(carrot) < 0) {
            throw new UserException(UserError.CARROT_NOT_ENOUGH);
        }
        this.carrot = this.carrot.subtract(carrot);
    }
}
