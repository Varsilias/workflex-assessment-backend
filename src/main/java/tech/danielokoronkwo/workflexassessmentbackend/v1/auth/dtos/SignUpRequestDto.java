package tech.danielokoronkwo.workflexassessmentbackend.v1.auth.dtos;

import jakarta.validation.constraints.*;

public class SignUpRequestDto {

    @NotBlank(message = "Firstname cannot be empty")
    private String firstName;
    @NotBlank(message = "Lastname cannot be empty")
    private String lastName;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    @Pattern(regexp = "^(?!.*example\\.com).*$", message = "example.com emails are not allowed")
    private String email;
    @NotBlank(message = "Phone Number cannot be empty")
    private String phoneNumber;
    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$", message = "Password must be 8 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.")
    private String password;

    public SignUpRequestDto() {
    }

    public SignUpRequestDto(String firstName, String lastName, String email, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignUpRequestDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}