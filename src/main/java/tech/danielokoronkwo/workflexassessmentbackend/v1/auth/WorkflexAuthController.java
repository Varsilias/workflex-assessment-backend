package tech.danielokoronkwo.workflexassessmentbackend.v1.auth;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.danielokoronkwo.workflexassessmentbackend.common.constants.ResponseConstants;
import tech.danielokoronkwo.workflexassessmentbackend.common.dtos.SuccessResponseDto;
import tech.danielokoronkwo.workflexassessmentbackend.v1.auth.dtos.SignInRequestDto;
import tech.danielokoronkwo.workflexassessmentbackend.v1.auth.dtos.SignUpRequestDto;
import tech.danielokoronkwo.workflexassessmentbackend.v1.auth.user.UserEntity;
import tech.danielokoronkwo.workflexassessmentbackend.v1.workation.WorkationEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class WorkflexAuthController {
    private static final Logger logger = LoggerFactory.getLogger(WorkflexAuthController.class);
    private final WorkflexAuthService workflexAuthService;

    public WorkflexAuthController(WorkflexAuthService workflexAuthService) {
        this.workflexAuthService = workflexAuthService;
    }

    @PostMapping("/workflex/register")
    public ResponseEntity<SuccessResponseDto> register(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        logger.info(signUpRequestDto.toString());
        UserEntity userEntity = workflexAuthService.registerUser(signUpRequestDto);
        SuccessResponseDto successResponseDto = new SuccessResponseDto(ResponseConstants.SUCCESS, "User registered successfully",null, userEntity);

        return new ResponseEntity<>(successResponseDto, HttpStatus.CREATED);
    }
    @PostMapping("/workflex/login")
    public ResponseEntity<SuccessResponseDto> login(@Valid @RequestBody SignInRequestDto signInRequestDto) {
        logger.info(signInRequestDto.toString());

        Map<String, Object> response = workflexAuthService.loginUser(signInRequestDto);
        SuccessResponseDto successResponseDto = new SuccessResponseDto(ResponseConstants.SUCCESS, "Login Successfully", null, response);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }

    @GetMapping("/workflex/workations")
    public ResponseEntity<SuccessResponseDto> workations() {
        logger.info("Fetching all workations");
        List<WorkationEntity> workations = workflexAuthService.getAllWorkations();
        SuccessResponseDto successResponseDto = new SuccessResponseDto(ResponseConstants.SUCCESS, "Workations retrieved Successfully", null, workations);


        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }
}


