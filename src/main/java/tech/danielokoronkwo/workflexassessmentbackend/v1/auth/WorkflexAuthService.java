package tech.danielokoronkwo.workflexassessmentbackend.v1.auth;

import com.password4j.Password;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import tech.danielokoronkwo.workflexassessmentbackend.common.exceptions.BadRequestException;
import tech.danielokoronkwo.workflexassessmentbackend.common.exceptions.UserAlreadyExistsException;
import tech.danielokoronkwo.workflexassessmentbackend.v1.auth.dtos.SignInRequestDto;
import tech.danielokoronkwo.workflexassessmentbackend.v1.auth.dtos.SignUpRequestDto;
import tech.danielokoronkwo.workflexassessmentbackend.v1.auth.user.UserEntity;
import tech.danielokoronkwo.workflexassessmentbackend.v1.auth.user.UserService;
import tech.danielokoronkwo.workflexassessmentbackend.v1.workation.WorkationEntity;
import tech.danielokoronkwo.workflexassessmentbackend.v1.workation.WorkationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WorkflexAuthService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflexAuthService.class);
    private final UserService userService;
    private final JwtService jwtService;

    private final WorkationService workationService;


    public WorkflexAuthService(UserService userService, JwtService jwtService, WorkationService workationService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.workationService = workationService;
    }


    public UserEntity registerUser(SignUpRequestDto signUpRequestDto) {
        boolean userExists = userService.getUser(signUpRequestDto.getEmail()).isPresent();

        logger.info("Signing Up User: {}", signUpRequestDto.getEmail());


        if (userExists) {
            throw new UserAlreadyExistsException("User with email " + signUpRequestDto.getEmail() + " already exists");
        }
        UserEntity userEntity = new UserEntity();

        String passwordHash = generatePasswordHash(signUpRequestDto.getPassword());

        userEntity.setEmail(signUpRequestDto.getEmail());
        userEntity.setFirstName(signUpRequestDto.getFirstName());
        userEntity.setPhoneNumber(signUpRequestDto.getPhoneNumber());
        userEntity.setLastName(signUpRequestDto.getLastName());
        userEntity.setPassword(passwordHash);

        return userService.createUser(userEntity);
    }

    public Map<String, Object> loginUser(SignInRequestDto signInRequestDto) {
        Optional<UserEntity> user = userService.getUser(signInRequestDto.getEmail());

        logger.info("Signing In User: {}", signInRequestDto.getEmail());

        if (user.isEmpty()) {
            throw new BadRequestException("Invalid login credentials");
        }

        boolean isPasswordMatch = comparePassword(user.orElseThrow().getPassword(), signInRequestDto.getPassword());

        if (!isPasswordMatch) {
            throw new BadRequestException("Invalid login credentials");
        }

        String accessToken = jwtService.generateAccessToken(signInRequestDto.getEmail());
        String refreshToken = jwtService.generateRefreshToken(signInRequestDto.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        response.put("user", user);

        return response;
    }

    public List<WorkationEntity> getAllWorkations() {
        return workationService.getAllWorkations();
    }

    private String generatePasswordHash(String password) {
        return Password.hash(password).withBcrypt().getResult();
    }

    private boolean comparePassword(String hashedPassword, String password) {
        return Password.check(password, hashedPassword).withBcrypt();
    }
}
