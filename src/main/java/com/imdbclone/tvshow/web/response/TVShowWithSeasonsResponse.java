package com.imdbclone.tvshow.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class TVShowWithSeasonsResponse {

    TVShowResponse tvShowResponse;

    List<TVShowSeasonResponse> tvShowSeasonResponses;

}
