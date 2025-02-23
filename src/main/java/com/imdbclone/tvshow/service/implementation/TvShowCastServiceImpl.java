package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.repository.TvShowCastRepository;
import com.imdbclone.tvshow.service.api.ITvShowCastService;
import com.imdbclone.tvshow.web.request.TvShowCastRequest;
import com.imdbclone.tvshow.web.request.TvShowCastUpdateRequest;
import com.imdbclone.tvshow.web.response.TvShowCastResponse;

import java.util.List;

public class TvShowCastServiceImpl implements ITvShowCastService {

    private final TvShowCastRepository tvShowCastRepository;

    public TvShowCastServiceImpl(TvShowCastRepository tvShowCastRepository){
        this.tvShowCastRepository = tvShowCastRepository;
    }

    @Override
    public List<TvShowCastResponse> getCastByTvShowId(Long tvShowId) {
        return List.of();
    }

    @Override
    public void addTvShowCast(TvShowCastRequest tvShowCastRequest) {

    }

    @Override
    public TvShowCastResponse updateTvShowCast(TvShowCastUpdateRequest tvShowCastUpdateRequest) {
        return null;
    }

    @Override
    public void deleteTvShowCastById(Long tvShowCastId) {

    }
}
