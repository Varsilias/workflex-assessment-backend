package tech.danielokoronkwo.workflexassessmentbackend.v1.auth.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import tech.danielokoronkwo.workflexassessmentbackend.common.entities.BaseEntity;

import java.util.Date;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    public UserEntity() {
    }

    public UserEntity(Long id, String publicId, Date createdAt, Date updatedAt, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(id, publicId, createdAt, updatedAt);
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
}
