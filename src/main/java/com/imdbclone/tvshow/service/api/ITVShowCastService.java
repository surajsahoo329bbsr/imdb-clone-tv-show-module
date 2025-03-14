package com.imdbclone.tvshow.service.api;

import com.imdbclone.tvshow.web.request.TVShowCastRequest;
import com.imdbclone.tvshow.web.response.TVShowCastResponse;

import java.util.List;

public interface ITVShowCastService {

    List<TVShowCastResponse> getCastsByShowId(Long showId);

    void addTVShowCast(TVShowCastRequest tvShowCastRequest);

    TVShowCastResponse updateTVShowCast(Long castId, TVShowCastRequest tvShowCastRequest);

    void deleteTVShowCastById(Long castId);

}
