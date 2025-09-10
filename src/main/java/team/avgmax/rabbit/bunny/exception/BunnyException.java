package team.avgmax.rabbit.bunny.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.avgmax.rabbit.global.dto.ErrorCode;

@Getter
@RequiredArgsConstructor
public class BunnyException extends RuntimeException {

    private final ErrorCode errorCode;
}
