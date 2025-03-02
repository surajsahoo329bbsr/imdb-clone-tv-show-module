package com.imdbclone.tvshow.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TVShowCastUpdateRequest {

    @NotBlank(message = "TV Show Cast ID cannot be blank")
    private Long castId;

    private Long personId;

    private Integer seasonNumber;

    private String characterName;

    private String roleType;

}
