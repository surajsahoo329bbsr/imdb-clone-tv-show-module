package com.imdbclone.tvshow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class TVShowWithGenreDTO {

    private Long tvShowId;

    private String title;

    private List<String> genres;

    private LocalDateTime releaseYear;

    private String language;

    private Integer seasonsCount;

    private Float score;

    private String posterUrl;

    private String description;

    private boolean status;

    private boolean isDeleted;

}
