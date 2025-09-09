package team.avgmax.rabbit.funding.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FundingException extends RuntimeException {
    private final FundingError error;
    
    @Override
    public String getMessage() {
        return error.getMessage();
    }
}
