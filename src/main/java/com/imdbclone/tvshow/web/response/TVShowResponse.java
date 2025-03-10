package com.imdbclone.tvshow.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class TVShowResponse {

    private Long id;

    private String title;

    private LocalDateTime releaseYear;

    private String language;

    private Integer seasonsCount;

    private Float score;

    private String posterUrl;

    private String description;

    private boolean status;

}
