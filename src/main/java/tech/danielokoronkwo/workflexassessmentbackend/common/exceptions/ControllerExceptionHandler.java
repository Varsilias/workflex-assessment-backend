package tech.danielokoronkwo.workflexassessmentbackend.common.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech.danielokoronkwo.workflexassessmentbackend.common.constants.ResponseConstants;
import tech.danielokoronkwo.workflexassessmentbackend.common.dtos.ErrorResponseDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                                  HttpServletRequest request) {
        logger.warn("handleMethodArgumentNotValidException Exception Handler: {}", ex.getMessage());
        List<String> errors = new ArrayList<>();
        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        ErrorResponseDto ErrorResponseDto = new ErrorResponseDto(ResponseConstants.ERROR, errors.get(0), errors, null);

        return new ResponseEntity<>(ErrorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
                                                                                 HttpServletRequest request) {
        logger.warn("handleHttpMessageNotReadableException Exception Handler: {}", ex.getMessage());

        // We create our own message because the generated error message is not helpful
        // to the client
        String message = "Invalid request body";
        List<String> error = Collections.singletonList(message);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ResponseConstants.ERROR, message, error, null);

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                           HttpServletRequest request) {
        logger.warn("handleResourceNotFoundException Exception Handler: {}", ex.getMessage());

        List<String> error = Collections.singletonList(ex.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ResponseConstants.ERROR, ex.getMessage(), error, null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException(UserAlreadyExistsException ex,
                                                                            HttpServletRequest request) {
        logger.warn("handleUserAlreadyExistsException Exception Handler: {}", ex.getMessage());

        List<String> error = Collections.singletonList(ex.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ResponseConstants.ERROR, ex.getMessage(), error, null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(BadRequestException ex,
                                                                     HttpServletRequest request) {
        logger.warn("handleBadRequestException Exception Handler: {}", ex.getMessage());

        List<String> error = Collections.singletonList(ex.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ResponseConstants.ERROR, ex.getMessage(), error, null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
}
