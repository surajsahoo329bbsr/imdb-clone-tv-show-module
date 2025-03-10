package com.imdbclone.tvshow.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class TVShowSeasonWithEpisodesResponse {

    TVShowSeasonResponse tvShowSeasonResponse;

    List<TVShowEpisodeResponse> tvShowEpisodeResponses;
}
