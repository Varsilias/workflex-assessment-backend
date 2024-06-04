package tech.danielokoronkwo.workflexassessmentbackend.v1.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SignInRequestDto {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    @Pattern(regexp = "^(?!.*example\\.com).*$", message = "example.com emails are not allowed")
    private String email;

    // We could remove the Password pattern validation since
    // it could give potential harmful actors hint on how
    // to guess the password
    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$", message = "Password must be 8 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.")
    private String password;

    public SignInRequestDto() {
    }

    public SignInRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignInRequestDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
