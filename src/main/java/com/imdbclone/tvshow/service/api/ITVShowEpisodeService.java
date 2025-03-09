package com.imdbclone.tvshow.service.api;

import com.imdbclone.tvshow.web.request.TVShowEpisodeRequest;
import com.imdbclone.tvshow.web.response.TVShowEpisodeResponse;

import java.util.List;

public interface ITVShowEpisodeService {

    TVShowEpisodeResponse getTVShowEpisodeById(Long episodeId);

    List<TVShowEpisodeResponse> getTVShowEpisodesByShowId(Long showId);

    void addTVShowEpisode(TVShowEpisodeRequest tvShowEpisodeRequest);

    TVShowEpisodeResponse updateTVShowEpisodeById(Long episodeId, TVShowEpisodeRequest tvShowEpisodeRequest);

    void deleteTVShowEpisodeById(Long episodeId);
}
