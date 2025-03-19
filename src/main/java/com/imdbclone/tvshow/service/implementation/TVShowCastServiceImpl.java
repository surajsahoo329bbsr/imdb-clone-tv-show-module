package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.dto.TVShowCastPersonDTO;
import com.imdbclone.tvshow.entity.TVShowCast;
import com.imdbclone.tvshow.repository.TVShowCastRepository;
import com.imdbclone.tvshow.service.api.ITVShowCastService;
import com.imdbclone.tvshow.service.client.UserServiceClient;
import com.imdbclone.tvshow.web.request.TVShowCastRequest;
import com.imdbclone.tvshow.web.response.TVShowCastResponse;
import jakarta.persistence.EntityNotFoundException;
import util.JWTUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TVShowCastServiceImpl implements ITVShowCastService {

    private final TVShowCastRepository tvShowCastRepository;
    private final UserServiceClient userServiceClient;
    private final JWTUtils jwtUtils;

    public TVShowCastServiceImpl(TVShowCastRepository tvShowCastRepository, UserServiceClient userServiceClient, JWTUtils jwtUtils) {
        this.tvShowCastRepository = tvShowCastRepository;
        this.userServiceClient = userServiceClient;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public List<TVShowCastResponse> getCastsByShowId(Long showId) {
        List<TVShowCast> tvShowCasts = tvShowCastRepository.findTVShowCastByShowId(showId);
        List<Long> personIds = tvShowCasts.stream()
                .map(TVShowCast::getPersonId)
                .distinct()
                .toList();

        Map<Long, TVShowCastPersonDTO> tvShowCastMap = userServiceClient.getTVShowCastsByPersonIds(personIds);

        return tvShowCasts.stream()
                .map(tvShowCast -> new TVShowCastResponse(
                        tvShowCast.getId(),
                        tvShowCast.getShowId(),
                        tvShowCastMap.getOrDefault(tvShowCast.getPersonId(), null),
                        tvShowCast.getCharacterName(),
                        tvShowCast.getSeasonNumber(),
                        tvShowCast.getRoleType()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void addTVShowCast(TVShowCastRequest tvShowCastRequest) {
        TVShowCast tvShowCast =
                TVShowCast.builder()
                        .showId(tvShowCastRequest.getShowId())
                        .personId(tvShowCastRequest.getPersonId())
                        .seasonNumber(tvShowCastRequest.getSeasonNumber())
                        .characterName(tvShowCastRequest.getCharacterName())
                        .roleType(tvShowCastRequest.getRoleType())
                        .createdBy(jwtUtils.getAdminIdFromJwt())
                        .createdAt(LocalDateTime.now())
                        .build();
        tvShowCastRepository.save(tvShowCast);
    }

    @Override
    public TVShowCastResponse updateTVShowCast(Long castId, TVShowCastRequest tvShowCastRequest) {
        TVShowCastPersonDTO tvShowCastPersonDummyDto = userServiceClient.getTVShowCastByPersonId(castId);
        TVShowCast tvShowCast = tvShowCastRepository
                .findById(castId)
                .filter(filterTVShowCast -> !filterTVShowCast.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("TV Show Cast entry not found"));

        tvShowCast.setPersonId(tvShowCastRequest.getPersonId());
        tvShowCast.setSeasonNumber(tvShowCastRequest.getSeasonNumber());
        tvShowCast.setCharacterName(tvShowCastRequest.getCharacterName());
        tvShowCast.setRoleType(tvShowCastRequest.getRoleType());
        tvShowCast.setUpdatedAt(LocalDateTime.now());
        tvShowCast.setUpdatedBy(jwtUtils.getAdminIdFromJwt());

        TVShowCast updatedCast = tvShowCastRepository.save(tvShowCast);

        return TVShowCastResponse.builder()
                .castId(updatedCast.getId())
                .showId(updatedCast.getShowId())
                .tvShowCastPersonDto(tvShowCastPersonDummyDto)
                .characterName(updatedCast.getCharacterName())
                .seasonNumber(updatedCast.getSeasonNumber())
                .roleType(updatedCast.getRoleType())
                .build();
    }

    @Override
    public void deleteTVShowCastById(Long castId) {
        TVShowCast tvShowCast = tvShowCastRepository.findById(castId)
                .filter(cast -> !cast.isDeleted()) // Skip already deleted records
                .orElseThrow(() -> new EntityNotFoundException("TV Show Cast ID " + castId + " is unavailable"));

        tvShowCast.setDeleted(true);
        tvShowCast.setDeletedAt(LocalDateTime.now());
        tvShowCast.setDeletedBy(jwtUtils.getAdminIdFromJwt());

        tvShowCastRepository.save(tvShowCast);
    }
}
