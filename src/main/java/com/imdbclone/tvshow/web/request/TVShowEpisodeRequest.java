package com.imdbclone.tvshow.web.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TVShowEpisodeRequest {

    @NotNull(message = "TV Show Season Id cannot be blank")
    private Long seasonId;

    @NotNull(message = "TV Show Episode's Number cannot be blank")
    private Integer episodeNumber;

    @NotBlank(message = "TV Show Episode's Title cannot be blank")
    private String title;

    @NotBlank(message = "TV Show Episode's Description cannot be blank")
    private String description;

    @Positive(message = "TV Show Episode Duration cannot be blank")
    private Integer duration;

    @NotNull(message = "TV Show Episode's Release Date cannot be null")
    private LocalDateTime releaseDate;

    @Min(value = 1, message = "TV Show Episode's minimum score cannot be less than 1.0")
    @Max(value = 10, message = "TV Show Episode's minimum score cannot be more than 10.0")
    private Float score;

}
