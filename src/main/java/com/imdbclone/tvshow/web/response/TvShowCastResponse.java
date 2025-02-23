package com.imdbclone.tvshow.web.response;

import com.imdbclone.tvshow.dto.TvShowCastPersonDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TvShowCastResponse {

    private Long id;

    private Long tvShowId;

    private TvShowCastPersonDto tvShowCastPersonDto;

    private String characterName;

    private Integer seasonNumber;

    private String roleType;
}
