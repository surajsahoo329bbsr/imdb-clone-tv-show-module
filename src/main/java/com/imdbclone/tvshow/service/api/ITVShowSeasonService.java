package com.imdbclone.tvshow.service.api;

import com.imdbclone.tvshow.web.request.TVShowSeasonRequest;
import com.imdbclone.tvshow.web.response.TVShowSeasonResponse;
import com.imdbclone.tvshow.web.response.TVShowWithSeasonsResponse;

public interface ITVShowSeasonService {

    TVShowSeasonResponse getTVShowSeasonById(Long seasonId);

    TVShowWithSeasonsResponse getTVShowSeasonsByShowId(Long showId, Integer pageNumber, Integer pageSize, boolean sortByLatestFirst);

    void addTVShowSeason(TVShowSeasonRequest tvShowSeasonRequest);

    TVShowSeasonResponse updateTVShowSeasonById(Long seasonId, TVShowSeasonRequest tvShowSeasonRequest);

    void deleteTVShowSeasonById(Long seasonId);
}
