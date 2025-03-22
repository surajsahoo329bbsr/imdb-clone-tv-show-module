package com.imdbclone.tvshow.service.implementation;


import com.imdbclone.tvshow.entity.TVShow;
import com.imdbclone.tvshow.entity.TVShowGenre;
import com.imdbclone.tvshow.entity.TVShowSeason;
import com.imdbclone.tvshow.repository.TVShowGenreRepository;
import com.imdbclone.tvshow.repository.TVShowRepository;
import com.imdbclone.tvshow.repository.TVShowSeasonRepository;
import com.imdbclone.tvshow.service.api.ITVShowSeasonService;
import com.imdbclone.tvshow.service.client.AdminServiceClient;
import com.imdbclone.tvshow.web.request.TVShowSeasonRequest;
import com.imdbclone.tvshow.web.response.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.imdbclone.tvshow.util.JWTUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TVShowSeasonServiceImpl implements ITVShowSeasonService {

    private final TVShowRepository tvShowRepository;
    private final TVShowSeasonRepository tvShowSeasonRepository;
    private final TVShowGenreRepository tvShowGenreRepository;
    private final AdminServiceClient adminServiceClient;
    private final JWTUtils jwtUtils;

    public TVShowSeasonServiceImpl(TVShowSeasonRepository tvShowSeasonRepository, TVShowRepository tvShowRepository, TVShowGenreRepository tvShowGenreRepository, AdminServiceClient adminServiceClient, JWTUtils jwtUtils) {
        this.tvShowSeasonRepository = tvShowSeasonRepository;
        this.tvShowRepository = tvShowRepository;
        this.tvShowGenreRepository = tvShowGenreRepository;
        this.adminServiceClient = adminServiceClient;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public TVShowSeasonResponse getTVShowSeasonById(Long seasonId) {
        return tvShowSeasonRepository.findById(seasonId)
                .filter(tvShowSeason -> !tvShowSeason.isDeleted())
                .map(tvShowSeason -> new TVShowSeasonResponse(
                        tvShowSeason.getId(),
                        tvShowSeason.getShowId(),
                        tvShowSeason.getSeasonNumber(),
                        tvShowSeason.getTotalEpisodes(),
                        tvShowSeason.getDescription(),
                        tvShowSeason.getReleaseYear()
                )).orElse(null);
    }

    @Override
    public TVShowWithSeasonsResponse getTVShowSeasonsByShowId(Long showId, Integer pageNumber, Integer pageSize, boolean sortByLatestFirst) {
        TVShow tvShow = tvShowRepository.findById(showId)
                .filter(season -> !season.isDeleted())
                .orElseThrow(() -> new RuntimeException("TV Show Not Found !"));

        Sort sort = sortByLatestFirst ? Sort.by("id").descending() :
                Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        List<TVShowGenre> tvShowGenres = tvShowGenreRepository.findTVShowGenreByShowId(showId);
        List<Long> genreIds = tvShowGenres.stream().map(TVShowGenre::getGenreId).toList();
        Map<Long, String> genres = adminServiceClient.getGenreNamesById(genreIds);

        List<String> genreList = tvShowGenreRepository.findTVShowGenreByShowId(showId).stream()
                .map(t -> genres.getOrDefault(t.getGenreId(), "Unknown"))
                .toList();

        List<TVShowSeasonResponse> tvShowSeasonResponsesList = tvShowSeasonRepository.findTVShowSeasonsByShowId(showId, pageable)
                .stream()
                .map(tvShowSeason -> TVShowSeasonResponse.builder()
                        .showId(tvShowSeason.getShowId())
                        .seasonId(tvShowSeason.getId())
                        .seasonNumber(tvShowSeason.getSeasonNumber())
                        .totalEpisodes(tvShowSeason.getTotalEpisodes())
                        .description(tvShowSeason.getDescription())
                        .releaseYear(tvShowSeason.getReleaseYear())
                        .build()
                )
                .toList();

        return TVShowWithSeasonsResponse
                .builder()
                .tvShowResponse(TVShowResponse.builder()
                        .id(tvShow.getId())
                        .title(tvShow.getTitle())
                        .genres(genreList)
                        .releaseYear(tvShow.getReleaseYear())
                        .language(tvShow.getLanguage())
                        .seasonsCount(tvShow.getSeasonsCount())
                        .score(tvShow.getScore())
                        .posterUrl(tvShow.getPosterUrl())
                        .description(tvShow.getDescription())
                        .status(tvShow.isStatus())
                        .build())
                .tvShowSeasonResponses(tvShowSeasonResponsesList)
                .build();
    }

    @Override
    public void addTVShowSeason(TVShowSeasonRequest tvShowSeasonRequest) {
        TVShowSeason tvShowSeason = TVShowSeason.builder()
                .showId(tvShowSeasonRequest.getShowId())
                .seasonNumber(tvShowSeasonRequest.getSeasonNumber())
                .description(tvShowSeasonRequest.getDescription())
                .totalEpisodes(tvShowSeasonRequest.getTotalEpisodes())
                .releaseYear(tvShowSeasonRequest.getReleaseYear())
                .createdBy(jwtUtils.getAdminIdFromJwt())
                .createdAt(LocalDateTime.now())
                .build();
        tvShowSeasonRepository.save(tvShowSeason);
    }

    @Override
    public TVShowSeasonResponse updateTVShowSeasonById(Long seasonId, TVShowSeasonRequest tvShowSeasonRequest) {
        TVShowSeason tvShowSeason = tvShowSeasonRepository.findById(seasonId)
                .filter(season -> !season.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("TV Show Season entry not found"));

        tvShowSeason.setShowId(tvShowSeasonRequest.getShowId());
        tvShowSeason.setSeasonNumber(tvShowSeason.getSeasonNumber());
        tvShowSeason.setDescription(tvShowSeasonRequest.getDescription());
        tvShowSeason.setTotalEpisodes(tvShowSeasonRequest.getTotalEpisodes());
        tvShowSeason.setReleaseYear(tvShowSeason.getReleaseYear());
        tvShowSeason.setUpdatedBy(jwtUtils.getAdminIdFromJwt());
        tvShowSeason.setUpdatedAt(LocalDateTime.now());

        TVShowSeason updatedSeason = tvShowSeasonRepository.save(tvShowSeason);

        return TVShowSeasonResponse.builder()
                .seasonId(updatedSeason.getId())
                .showId(updatedSeason.getShowId())
                .seasonNumber(updatedSeason.getSeasonNumber())
                .description(updatedSeason.getDescription())
                .totalEpisodes(updatedSeason.getTotalEpisodes())
                .releaseYear(updatedSeason.getReleaseYear())
                .build();
    }

    @Override
    public void deleteTVShowSeasonById(Long seasonId) {
        TVShowSeason tvShowSeason = tvShowSeasonRepository.findById(seasonId)
                .filter(season -> !season.isDeleted()) // Skip already deleted records
                .orElseThrow(() -> new EntityNotFoundException("TV Show Season ID " + seasonId + " is unavailable"));

        tvShowSeason.setDeleted(true);
        tvShowSeason.setDeletedAt(LocalDateTime.now());
        tvShowSeason.setDeletedBy(jwtUtils.getAdminIdFromJwt());

        tvShowSeasonRepository.save(tvShowSeason);
    }

}
