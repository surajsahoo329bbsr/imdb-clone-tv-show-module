package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.entity.TvShow;
import com.imdbclone.tvshow.repository.TvShowRepository;
import com.imdbclone.tvshow.service.api.ITvShowService;
import com.imdbclone.tvshow.web.response.TvShowResponse;
import com.imdbclone.tvshow.web.request.TvShowRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class TvShowServiceImpl implements ITvShowService {

    private final TvShowRepository tvShowRepository;

    public TvShowServiceImpl(TvShowRepository tvShowRepository){
        this.tvShowRepository = tvShowRepository;
    }

    @Override
    public List<TvShowResponse> getAllTvShows(Integer pageNumber, Integer pageSize, Boolean isSortOrderDescending) {
        return List.of();
    }

    @Override
    public TvShowResponse getTvShowById(Long id) {
        return null;
    }

    @Override
    public TvShow addTvShow(TvShowRequest tvShowRequest) {
        return null;
    }

    @Override
    public void uploadTvShows(MultipartFile tvShowsCsvFile) {

    }

    @Override
    public TvShow updateTvShowById(Long id, TvShowRequest tvShowRequest) {
        return null;
    }

    @Override
    public void deleteTvShowById(Long id) {

    }
}
