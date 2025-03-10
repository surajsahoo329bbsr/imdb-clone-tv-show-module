package com.imdbclone.tvshow.service.api;

import com.imdbclone.tvshow.web.request.TVShowEpisodeRequest;
import com.imdbclone.tvshow.web.response.TVShowEpisodeResponse;
import com.imdbclone.tvshow.web.response.TVShowSeasonWithEpisodesResponse;

public interface ITVShowEpisodeService {

    TVShowEpisodeResponse getTVShowEpisodeById(Long episodeId);

    TVShowSeasonWithEpisodesResponse getTVShowEpisodesBySeasonId(Long seasonId, Integer pageNumber, Integer pageSize, boolean sortByLatestFirst);

    void addTVShowEpisode(TVShowEpisodeRequest tvShowEpisodeRequest);

    TVShowEpisodeResponse updateTVShowEpisodeById(Long episodeId, TVShowEpisodeRequest tvShowEpisodeRequest);

    void deleteTVShowEpisodeById(Long episodeId);
}
