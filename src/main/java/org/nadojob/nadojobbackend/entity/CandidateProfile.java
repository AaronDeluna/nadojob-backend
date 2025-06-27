package org.nadojob.nadojobbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candidate_profiles")
public class CandidateProfile {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_candidate_user")
    )
    private User user;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "desired_salary")
    private Integer desiredSalary;

    @Enumerated(EnumType.STRING)
    private Currency desiredCurrency;

    @Column(name = "location_city")
    private String locationCity;

    @Column(name = "about_me")
    private String aboutMe;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(columnDefinition = "jsonb")
    private String skills;

    @Column(columnDefinition = "jsonb")
    private String languages;

    @Column(name = "work_experience", columnDefinition = "jsonb")
    private String workExperience;

    @Column(columnDefinition = "jsonb")
    private String education;

    @Column(name = "contacts_extra", columnDefinition = "jsonb")
    private String contactsExtra;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
