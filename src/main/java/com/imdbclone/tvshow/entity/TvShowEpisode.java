package com.imdbclone.tvshow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
public class TvShowEpisode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tvShowSeasonId;

    @Column(nullable = false)
    private Integer episodeNumber;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Positive
    private Integer duration;

    @Column(nullable = false)
    private LocalDateTime releaseDate;

    @Min(value=1, message = "TV Show Episode's minimum score cannot be less than 1.0")
    @Max(value=10, message = "TV Show Episode's minimum score cannot be more than 10.0")
    private Float score;

    private Boolean isDeleted;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant deletedAt;
}