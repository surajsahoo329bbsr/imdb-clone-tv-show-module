package com.imdbclone.tvshow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "tv_show_season")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TVShowSeason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "TV Show Season's TV Show ID cannot be blank")
    private Long showId;

    @Column(nullable = false)
    @Positive(message = "TV Show Season's season number must be a positive number")
    private Integer seasonNumber;

    @Column(nullable = false)
    @Positive(message = "TV Show Season's total episodes must be a positive number")
    private Integer totalEpisodes;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "TV Show Season's release year cannot be blank")
    private LocalDateTime releaseYear;

    private boolean isDeleted = false;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant deletedAt;
}