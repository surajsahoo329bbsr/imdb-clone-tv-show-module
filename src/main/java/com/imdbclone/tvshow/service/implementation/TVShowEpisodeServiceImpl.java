package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.entity.TVShowEpisode;
import com.imdbclone.tvshow.entity.TVShowSeason;
import com.imdbclone.tvshow.repository.TVShowEpisodeRepository;
import com.imdbclone.tvshow.repository.TVShowSeasonRepository;
import com.imdbclone.tvshow.service.api.ITVShowEpisodeService;
import com.imdbclone.tvshow.web.request.TVShowEpisodeRequest;
import com.imdbclone.tvshow.web.response.TVShowEpisodeResponse;
import com.imdbclone.tvshow.web.response.TVShowSeasonResponse;
import com.imdbclone.tvshow.web.response.TVShowSeasonWithEpisodesResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import util.JWTUtils;

import java.time.LocalDateTime;
import java.util.List;

public class TVShowEpisodeServiceImpl implements ITVShowEpisodeService {

    private final TVShowEpisodeRepository tvShowEpisodeRepository;
    private final TVShowSeasonRepository tvShowSeasonRepository;
    private final JWTUtils jwtUtils;

    public TVShowEpisodeServiceImpl(TVShowEpisodeRepository tvShowEpisodeRepository, TVShowSeasonRepository tvShowSeasonRepository, JWTUtils jwtUtils) {
        this.tvShowEpisodeRepository = tvShowEpisodeRepository;
        this.tvShowSeasonRepository = tvShowSeasonRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public TVShowEpisodeResponse getTVShowEpisodeById(Long episodeId) {
        return tvShowEpisodeRepository.findById(episodeId)
                .filter(episode -> !episode.isDeleted())
                .map(tvShowEpisode -> TVShowEpisodeResponse.builder()
                        .episodeId(tvShowEpisode.getId())
                        .seasonId(tvShowEpisode.getSeasonId())
                        .episodeNumber(tvShowEpisode.getEpisodeNumber())
                        .title(tvShowEpisode.getTitle())
                        .description(tvShowEpisode.getDescription())
                        .duration(tvShowEpisode.getDuration())
                        .releaseDate(tvShowEpisode.getReleaseDate())
                        .score(tvShowEpisode.getScore())
                        .build()
                ).orElse(null);
    }

    @Override
    public TVShowSeasonWithEpisodesResponse getTVShowEpisodesBySeasonId(Long seasonId, Integer pageNumber, Integer pageSize, boolean sortByLatestFirst) {
        TVShowSeason tvShowSeason = tvShowSeasonRepository.findById(seasonId)
                .filter(season -> !season.isDeleted())
                .orElseThrow(() -> new RuntimeException("TV Show Season Not Found !"));

        Sort sort = sortByLatestFirst ? Sort.by("id").descending() :
                Sort.by("id").ascending();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        List<TVShowEpisodeResponse> tvShowEpisodeResponsesList = tvShowEpisodeRepository.findTVShowEpisodesBySeasonId(seasonId, pageable)
                .stream()
                .map(tvShowEpisode -> TVShowEpisodeResponse.builder()
                        .episodeId(tvShowEpisode.getId())
                        .seasonId(tvShowEpisode.getSeasonId())
                        .episodeNumber(tvShowEpisode.getEpisodeNumber())
                        .title(tvShowEpisode.getTitle())
                        .description(tvShowEpisode.getDescription())
                        .duration(tvShowEpisode.getDuration())
                        .releaseDate(tvShowEpisode.getReleaseDate())
                        .score(tvShowEpisode.getScore())
                        .build())
                .toList();

        return TVShowSeasonWithEpisodesResponse
                .builder()
                .tvShowSeasonResponse(
                        TVShowSeasonResponse.builder()
                                .showId(tvShowSeason.getShowId())
                                .seasonId(tvShowSeason.getId())
                                .seasonNumber(tvShowSeason.getSeasonNumber())
                                .totalEpisodes(tvShowSeason.getTotalEpisodes())
                                .description(tvShowSeason.getDescription())
                                .releaseYear(tvShowSeason.getReleaseYear())
                                .build()
                )
                .tvShowEpisodeResponses(tvShowEpisodeResponsesList)
                .build();
    }

    @Override
    public void addTVShowEpisode(TVShowEpisodeRequest tvShowEpisodeRequest) {
        TVShowEpisode tvShowEpisode = TVShowEpisode.builder()
                .seasonId(tvShowEpisodeRequest.getSeasonId())
                .episodeNumber(tvShowEpisodeRequest.getEpisodeNumber())
                .title(tvShowEpisodeRequest.getTitle())
                .description(tvShowEpisodeRequest.getDescription())
                .duration(tvShowEpisodeRequest.getDuration())
                .releaseDate(tvShowEpisodeRequest.getReleaseDate())
                .score(tvShowEpisodeRequest.getScore())
                .createdBy(jwtUtils.getAdminIdFromJwt())
                .createdAt(LocalDateTime.now())
                .build();
        tvShowEpisodeRepository.save(tvShowEpisode);
    }

    @Override
    public TVShowEpisodeResponse updateTVShowEpisodeById(Long episodeId, TVShowEpisodeRequest tvShowEpisodeRequest) {
        TVShowEpisode tvShowEpisode = tvShowEpisodeRepository.findById(episodeId)
                .filter(season -> !season.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("TV Show Episode entry not found"));

        tvShowEpisode.setId(episodeId);
        tvShowEpisode.setSeasonId(tvShowEpisodeRequest.getSeasonId());
        tvShowEpisode.setEpisodeNumber(tvShowEpisodeRequest.getEpisodeNumber());
        tvShowEpisode.setTitle(tvShowEpisodeRequest.getTitle());
        tvShowEpisode.setDescription(tvShowEpisodeRequest.getDescription());
        tvShowEpisode.setDuration(tvShowEpisodeRequest.getDuration());
        tvShowEpisode.setReleaseDate(tvShowEpisodeRequest.getReleaseDate());
        tvShowEpisode.setScore(tvShowEpisodeRequest.getScore());
        tvShowEpisode.setUpdatedBy(jwtUtils.getAdminIdFromJwt());
        tvShowEpisode.setUpdatedAt(LocalDateTime.now());

        TVShowEpisode updatedEpisode = tvShowEpisodeRepository.save(tvShowEpisode);

        return TVShowEpisodeResponse.builder()
                .episodeId(updatedEpisode.getId())
                .seasonId(updatedEpisode.getSeasonId())
                .episodeNumber(updatedEpisode.getEpisodeNumber())
                .title(updatedEpisode.getTitle())
                .description(updatedEpisode.getDescription())
                .duration(updatedEpisode.getDuration())
                .releaseDate(updatedEpisode.getReleaseDate())
                .score(updatedEpisode.getScore())
                .build();
    }

    @Override
    public void deleteTVShowEpisodeById(Long episodeId) {
        TVShowEpisode tvShowEpisode = tvShowEpisodeRepository.findById(episodeId)
                .filter(episode -> !episode.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("TV Show Episode entry not found"));

        tvShowEpisode.setDeleted(true);
        tvShowEpisode.setDeletedAt(LocalDateTime.now());
        tvShowEpisode.setDeletedBy(jwtUtils.getAdminIdFromJwt());

        tvShowEpisodeRepository.save(tvShowEpisode);

    }
}
