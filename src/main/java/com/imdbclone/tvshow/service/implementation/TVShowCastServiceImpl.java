package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.repository.TVShowCastRepository;
import com.imdbclone.tvshow.service.api.ITVShowCastService;
import com.imdbclone.tvshow.web.request.TVShowCastRequest;
import com.imdbclone.tvshow.web.request.TVShowCastUpdateRequest;
import com.imdbclone.tvshow.web.response.TVShowCastResponse;

import java.util.List;

public class TVShowCastServiceImpl implements ITVShowCastService {

    private final TVShowCastRepository tvShowCastRepository;

    public TVShowCastServiceImpl(TVShowCastRepository tvShowCastRepository){
        this.tvShowCastRepository = tvShowCastRepository;
    }

    @Override
    public List<TVShowCastResponse> getCastByTVShowId(Long tvShowId) {
        return List.of();
    }

    @Override
    public void addTVShowCast(TVShowCastRequest tvShowCastRequest) {

    }

    @Override
    public TVShowCastResponse updateTVShowCast(TVShowCastUpdateRequest tvShowCastUpdateRequest) {
        return null;
    }

    @Override
    public void deleteTVShowCastById(Long tvShowCastId) {

    }
}
