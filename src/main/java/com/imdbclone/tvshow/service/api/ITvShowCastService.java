package com.imdbclone.tvshow.service.api;

import com.imdbclone.tvshow.web.request.TvShowCastRequest;
import com.imdbclone.tvshow.web.request.TvShowCastUpdateRequest;
import com.imdbclone.tvshow.web.response.TvShowCastResponse;

import java.util.List;

public interface ITvShowCastService {

    List<TvShowCastResponse> getCastByTvShowId(Long tvShowId);

    void addTvShowCast(TvShowCastRequest tvShowCastRequest);

    TvShowCastResponse updateTvShowCast(TvShowCastUpdateRequest tvShowCastUpdateRequest);

    void deleteTvShowCastById(Long tvShowCastId);

}
