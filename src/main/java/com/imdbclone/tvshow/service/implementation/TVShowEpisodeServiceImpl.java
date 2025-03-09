package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.entity.TVShowEpisode;
import com.imdbclone.tvshow.repository.TVShowEpisodeRepository;
import com.imdbclone.tvshow.service.api.ITVShowEpisodeService;
import com.imdbclone.tvshow.web.request.TVShowEpisodeRequest;
import com.imdbclone.tvshow.web.response.TVShowEpisodeResponse;
import jakarta.persistence.EntityNotFoundException;

import java.time.Instant;
import java.util.List;

public class TVShowEpisodeServiceImpl implements ITVShowEpisodeService {

    private final TVShowEpisodeRepository tvShowEpisodeRepository;

    public TVShowEpisodeServiceImpl(TVShowEpisodeRepository tvShowEpisodeRepository) {
        this.tvShowEpisodeRepository = tvShowEpisodeRepository;
    }

    @Override
    public TVShowEpisodeResponse getTVShowEpisodeById(Long episodeId) {
        return tvShowEpisodeRepository.findById(episodeId)
                .filter(episode -> !episode.isDeleted())
                .map(tvShowEpisode -> new TVShowEpisodeResponse(
                        tvShowEpisode.getId(),
                        tvShowEpisode.getSeasonId(),
                        tvShowEpisode.getEpisodeNumber(),
                        tvShowEpisode.getTitle(),
                        tvShowEpisode.getDescription(),
                        tvShowEpisode.getDuration(),
                        tvShowEpisode.getReleaseDate(),
                        tvShowEpisode.getScore()
                )).orElse(null);
    }

    @Override
    public List<TVShowEpisodeResponse> getTVShowEpisodesByShowId(Long showId) {
        return List.of();
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
        tvShowEpisode.setDeletedAt(Instant.now());

        tvShowEpisodeRepository.save(tvShowEpisode);

    }
}
