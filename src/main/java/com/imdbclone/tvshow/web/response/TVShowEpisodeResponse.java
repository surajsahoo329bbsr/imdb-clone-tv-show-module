package com.imdbclone.tvshow.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TVShowEpisodeResponse {

    private Long episodeId;

    private Long seasonId;

    private Integer episodeNumber;

    private String title;

    private String description;

    private Integer duration;

    private LocalDateTime releaseDate;

    private Float score;
}
