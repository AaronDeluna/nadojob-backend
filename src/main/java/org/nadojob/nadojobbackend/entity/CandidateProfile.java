package org.nadojob.nadojobbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.nadojob.nadojobbackend.dto.candidate_profile.EducationDto;
import org.nadojob.nadojobbackend.dto.candidate_profile.LanguageDto;
import org.nadojob.nadojobbackend.dto.candidate_profile.WorkExperienceDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candidate_profiles")
public class CandidateProfile {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String title;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

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

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<String> skills;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<LanguageDto> languages;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<WorkExperienceDto> workExperience;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<EducationDto> education;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> contactsExtra;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

