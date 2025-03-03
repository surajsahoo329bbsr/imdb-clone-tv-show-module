package com.imdbclone.tvshow.service.api;

import com.imdbclone.tvshow.web.request.TVShowCastRequest;
import com.imdbclone.tvshow.web.request.TVShowCastUpdateRequest;
import com.imdbclone.tvshow.web.response.TVShowCastResponse;

import java.util.List;

public interface ITVShowCastService {

    List<TVShowCastResponse> getCastByTVShowId(Long tvShowId);

    void addTVShowCast(TVShowCastRequest tvShowCastRequest);

    TVShowCastResponse updateTVShowCast(Long castId, TVShowCastUpdateRequest tvShowCastUpdateRequest);

    void deleteTVShowCastById(Long castId);

}
