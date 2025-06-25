package org.nadojob.nadojobbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String phone;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(nullable = false)
    private Double rating;

    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
