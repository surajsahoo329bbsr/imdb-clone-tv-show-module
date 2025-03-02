package com.imdbclone.tvshow.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TVShowCastRequest {

    @NotBlank(message = "TV Show Cast's TV Show ID cannot be blank")
    private Long tvShowId;

    @NotBlank(message = "TV Show Cast's person ID cannot be blank")
    private Long personId;

    @Positive(message = "TV Show Cast's total seasons must be a positive number")
    private Integer seasonNumber;

    @NotBlank(message = "TV Show Cast's character name cannot be blank")
    private String characterName;

    private String roleType;

}