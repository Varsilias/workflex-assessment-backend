package tech.danielokoronkwo.workflexassessmentbackend.common.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "public_id")
    private String publicId;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    public BaseEntity() {
    }

    public BaseEntity(Long id, String publicId, Date createdAt, Date updatedAt) {
        this.id = id;
        this.publicId = publicId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }


    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        UUID uuid = UUID.randomUUID();

        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.publicId = uuid.toString();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
