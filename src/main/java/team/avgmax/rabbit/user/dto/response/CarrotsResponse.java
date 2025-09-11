package team.avgmax.rabbit.user.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import team.avgmax.rabbit.user.entity.PersonalUser;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CarrotsResponse (
    String carrots
) {
    public static CarrotsResponse from(PersonalUser personalUser) {
        return CarrotsResponse.builder()
                .carrots(personalUser.getCarrot().toString())
                .build();
    }
}