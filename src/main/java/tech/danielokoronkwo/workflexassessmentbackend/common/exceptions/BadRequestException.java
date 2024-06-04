package tech.danielokoronkwo.workflexassessmentbackend.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseException {
    private static final long serialVersionUID = 1L;
    public BadRequestException(String message) {
        super(message);
    }
}
