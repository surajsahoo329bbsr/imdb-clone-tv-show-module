package com.imdbclone.tvshow.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class TVShowSeasonResponse {

    private Long seasonId;

    private Long showId;

    private Integer seasonNumber;

    private Integer totalEpisodes;

    private String description;

    private LocalDateTime releaseYear;
}
