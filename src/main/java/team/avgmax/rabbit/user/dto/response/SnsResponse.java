package team.avgmax.rabbit.user.dto.response;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import team.avgmax.rabbit.user.entity.Sns;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SnsResponse(
    String url,
    String image
) {
    public static SnsResponse from(Sns sns) {
        return SnsResponse.builder()
                .url(sns.getUrl())
                // .image()
                .build();
    }

    public static List<SnsResponse> from(List<Sns> sns) {
        return sns.stream()
                .map(SnsResponse::from)
                .toList();
    }
}
