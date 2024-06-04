package tech.danielokoronkwo.workflexassessmentbackend.v1.auth.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public Optional<UserEntity> getUser(String email) {
        return userRepository.findOneByEmail(email);
    }

}