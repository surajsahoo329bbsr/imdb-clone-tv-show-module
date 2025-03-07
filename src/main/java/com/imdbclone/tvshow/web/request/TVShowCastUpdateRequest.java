package com.imdbclone.tvshow.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TVShowCastUpdateRequest {

    @NotBlank(message = "TV Show ID cannot be blank")
    private Long tvShowId;

    @NotBlank(message = "TV Show Person ID cannot be blank")
    private Long personId;

    private Integer seasonNumber;

    private String characterName;

    private String roleType;

}
