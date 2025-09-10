package team.avgmax.rabbit.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException {
    private final UserError error;

    @Override
    public String getMessage() {
        return error.getMessage();
    }
}
