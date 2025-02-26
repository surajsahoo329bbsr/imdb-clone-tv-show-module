package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.entity.TvShow;
import com.imdbclone.tvshow.repository.TvShowRepository;
import com.imdbclone.tvshow.service.api.ITvShowService;
import com.imdbclone.tvshow.web.response.TvShowResponse;
import com.imdbclone.tvshow.web.request.TvShowRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

public class TvShowServiceImpl implements ITvShowService {

    private final TvShowRepository tvShowRepository;

    public TvShowServiceImpl(TvShowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }

    @Override
    public List<TvShowResponse> getAllTvShows(Integer pageNumber, Integer pageSize, Boolean sortByLatestFirst) {
        try {
            Sort sort = sortByLatestFirst ? Sort.by("id").descending() :
                    Sort.by("id").ascending();
            Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
            Page<TvShow> page = tvShowRepository.findAll(pageable);
            return
                    page
                            .stream()
                            .map(tvShow -> new TvShowResponse(
                                    tvShow.getId(),
                                    tvShow.getTitle(),
                                    tvShow.getReleaseYear(),
                                    tvShow.getLanguage(),
                                    tvShow.getSeasonsCount(),
                                    tvShow.getScore(),
                                    tvShow.getPosterUrl(),
                                    tvShow.getDescription(),
                                    tvShow.getStatus()
                            ))
                            .toList();
        } catch (IllegalArgumentException e) {
            //throw new CustomBadRequestException("Invalid pagination parameters: " + e.getMessage());
        } catch (DataAccessException e) {
            //throw new DatabaseException("Database error occurred while fetching TV shows.", e);
        } catch (NoSuchElementException e) {
            //throw new ResourceNotFoundException("No TV Shows found.");
        }
        return null;
    }

    @Override
    public TvShowResponse getTvShowById(Long id) {
        return tvShowRepository.findById(id)
                .map(tvShow -> new TvShowResponse(
                        tvShow.getId(),
                        tvShow.getTitle(),
                        tvShow.getReleaseYear(),
                        tvShow.getLanguage(),
                        tvShow.getSeasonsCount(),
                        tvShow.getScore(),
                        tvShow.getPosterUrl(),
                        tvShow.getDescription(),
                        tvShow.getStatus()))
                .orElse(null);

    }

    @Override
    public TvShow addTvShow(TvShowRequest tvShowRequest) {
        TvShow tvShow = TvShow.builder()
                .title(tvShowRequest.getTitle())
                .releaseYear(tvShowRequest.getReleaseYear())
                .language(tvShowRequest.getLanguage())
                .seasonsCount(tvShowRequest.getSeasonsCount())
                .posterUrl(tvShowRequest.getPosterUrl())
                .description(tvShowRequest.getDescription())
                .status(tvShowRequest.getStatus())
                .adminId(tvShowRequest.getAdminId())
                .build();
        return tvShowRepository.save(tvShow);
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
