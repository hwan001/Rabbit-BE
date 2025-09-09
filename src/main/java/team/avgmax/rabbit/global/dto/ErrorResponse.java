package team.avgmax.rabbit.global.dto;

import org.springframework.http.ResponseEntity;

import lombok.Builder;

@Builder
public record ErrorResponse(
    int status,
    String name,
    String message
) {
    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponse.builder()
                		.status(e.getStatus().value())
                        .name(e.name())
                        .message(e.getMessage())
                        .build());
    }
}
