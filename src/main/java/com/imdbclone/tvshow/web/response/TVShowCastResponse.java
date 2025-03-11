package com.imdbclone.tvshow.web.response;

import com.imdbclone.tvshow.dto.TVShowCastPersonDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TVShowCastResponse {

    private Long castId;

    private Long showId;

    private TVShowCastPersonDTO tvShowCastPersonDto;

    private String characterName;

    private Integer seasonNumber;

    private String roleType;
}
