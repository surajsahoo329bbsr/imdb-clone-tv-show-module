package com.imdbclone.tvshow.service.api;

import com.imdbclone.tvshow.dto.TVShowWithGenreDTO;
import com.imdbclone.tvshow.web.response.TVShowResponse;
import com.imdbclone.tvshow.web.request.TVShowRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ITVShowService {

    List<TVShowWithGenreDTO> getAllTVShows(Integer pageNumber, Integer pageSize, boolean sortByLatestFirst);

    TVShowResponse getTVShowById(Long id);

    void addTVShow(TVShowRequest tvShowRequest);

    UUID uploadTVShows(MultipartFile tvShowsCsvFile);

    TVShowResponse updateTVShowById(Long id, TVShowRequest tvShowRequest);

    void deleteTVShowById(Long id);
}
