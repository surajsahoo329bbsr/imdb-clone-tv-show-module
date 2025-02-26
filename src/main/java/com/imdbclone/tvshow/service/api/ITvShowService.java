package com.imdbclone.tvshow.service.api;

import com.imdbclone.tvshow.entity.TvShow;
import com.imdbclone.tvshow.web.response.TvShowResponse;
import com.imdbclone.tvshow.web.request.TvShowRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ITvShowService {

    List<TvShowResponse> getAllTvShows(Integer pageNumber, Integer pageSize, Boolean sortByLatestFirst);

    TvShowResponse getTvShowById(Long id);

    TvShow addTvShow(TvShowRequest tvShowRequest);

    void uploadTvShows(MultipartFile tvShowsCsvFile);

    TvShow updateTvShowById(Long id, TvShowRequest tvShowRequest);

    void deleteTvShowById(Long id);
}
