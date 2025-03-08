package com.imdbclone.tvshow.service.api;

import com.imdbclone.tvshow.entity.TVShow;
import com.imdbclone.tvshow.web.response.TVShowResponse;
import com.imdbclone.tvshow.web.request.TVShowRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ITVShowService {

    List<TVShowResponse> getAllTVShows(Integer pageNumber, Integer pageSize, boolean sortByLatestFirst);

    TVShowResponse getTVShowById(Long id);

    TVShow addTVShow(TVShowRequest tvShowRequest);

    UUID uploadTVShows(Long adminId, MultipartFile tvShowsCsvFile);

    TVShow updateTVShowById(Long id, TVShowRequest tvShowRequest);

    void deleteTVShowById(Long id);
}
