package com.imdbclone.tvshow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TvShow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "TV Show's title cannot be blank")
    private String title;

    @Column(columnDefinition = "DATETIME", nullable = false)
    @NotBlank(message = "TV Show's release year cannot be blank")
    private LocalDateTime releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "TV Show's language cannot be blank")
    private String language;

    @Column(nullable = false)
    @Positive(message = "TV Show's total seasons must be a positive number")
    private Integer seasonsCount;

    @URL(message = "TV Show's poster URL must be valid")
    @Column(length = 2048)
    private String posterUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean status;

    @Min(value=1, message = "TV Show's minimum score cannot be less than 1.0")
    @Max(value=10, message = "TV Show's minimum score cannot be more than 10.0")
    @Column(nullable = false)
    private Float score;

    @Column(nullable = false)
    @NotBlank(message = "TV Show's Admin ID cannot be blank")
    private Long adminId;

    private Boolean isDeleted;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant deletedAt;
}