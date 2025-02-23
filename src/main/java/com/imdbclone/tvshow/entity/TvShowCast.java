package com.imdbclone.tvshow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
public class TvShowCast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "TV Show Cast's TV Show ID cannot be blank")
    private Long tvShowId;

    @Column(nullable = false)
    @NotBlank(message = "TV Show Cast's person ID cannot be blank")
    private Long personId;

    @Column(nullable = false)
    @Positive(message = "TV Show Cast's total seasons must be a positive number")
    private Integer seasonNumber;

    @Column(nullable = false)
    @NotBlank(message = "TV Show Cast's character name cannot be blank")
    private String characterName;

    private String roleType;

    private Boolean isDeleted;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant deletedAt;
}