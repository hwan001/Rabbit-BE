package team.avgmax.rabbit.user.dto.response;

import java.time.LocalDate;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import team.avgmax.rabbit.user.entity.PersonalUser;
import team.avgmax.rabbit.user.entity.Skill;
import team.avgmax.rabbit.user.entity.enums.Position;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SpecResponse(
    String name,
    LocalDate birthdate,
    String image,
    String resume,
    Position position,
    List<SnsResponse> sns,
    List<String> skill,
    List<CertificationResponse> certification,
    List<CareerResponse> career,
    List<EducationResponse> education
) {
    public static SpecResponse from(PersonalUser user) {
        return SpecResponse.builder()
            .name(user.getName())
            .birthdate(user.getBirthdate())
            .image(user.getImage())
            .resume(user.getResume())
            .position(user.getPosition())
            .sns(SnsResponse.from(user.getSns()))
            .skill(user.getSkill().stream().map(Skill::getSkillName).toList())
            .certification(CertificationResponse.from(user.getCertification()))
            .career(CareerResponse.from(user.getCareer()))
            .education(EducationResponse.from(user.getEducations()))
            .build();
    }
}
