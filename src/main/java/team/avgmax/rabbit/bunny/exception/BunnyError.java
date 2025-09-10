package team.avgmax.rabbit.bunny.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team.avgmax.rabbit.global.dto.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum BunnyError implements ErrorCode {
    BUNNY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 버니를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
