package team.avgmax.rabbit.global.dto;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getStatus();
    String name();
    String getMessage();
}
