package com.imdbclone.tvshow.service.api;

import com.imdbclone.tvshow.web.request.TVShowSeasonRequest;
import com.imdbclone.tvshow.web.response.TVShowSeasonResponse;

public interface ITVShowSeasonService {

    TVShowSeasonResponse getTVShowSeasonById(Long seasonId);

    void addTVShowSeason(TVShowSeasonRequest tvShowSeasonRequest);

    TVShowSeasonResponse updateTVShowSeasonById(Long seasonId, TVShowSeasonRequest tvShowSeasonRequest);

    void deleteTVShowSeasonById(Long seasonId);
}
