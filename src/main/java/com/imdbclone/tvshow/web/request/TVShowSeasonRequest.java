package com.imdbclone.tvshow.web.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TVShowSeasonRequest {

    @NotNull(message = "TV Show Season's TV Show ID cannot be blank")
    private Long showId;

    @Positive(message = "TV Show Season's season number must be a positive number")
    private Integer seasonNumber;

    @Positive(message = "TV Show Season's total episodes must be a positive number")
    private Integer totalEpisodes;

    private String description;

    @NotNull(message = "TV Show Season's release year cannot be blank")
    private LocalDateTime releaseYear;
}
