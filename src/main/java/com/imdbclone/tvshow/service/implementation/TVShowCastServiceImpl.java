package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.dto.TVShowCastPersonDto;
import com.imdbclone.tvshow.entity.TVShowCast;
import com.imdbclone.tvshow.repository.TVShowCastRepository;
import com.imdbclone.tvshow.service.api.ITVShowCastService;
import com.imdbclone.tvshow.web.request.TVShowCastRequest;
import com.imdbclone.tvshow.web.request.TVShowCastUpdateRequest;
import com.imdbclone.tvshow.web.response.TVShowCastResponse;
import jakarta.persistence.EntityNotFoundException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TVShowCastServiceImpl implements ITVShowCastService {

    private final TVShowCastRepository tvShowCastRepository;

    public TVShowCastServiceImpl(TVShowCastRepository tvShowCastRepository) {
        this.tvShowCastRepository = tvShowCastRepository;
    }

    @Override
    public List<TVShowCastResponse> getCastByTVShowId(Long tvShowId) {
        List<TVShowCast> tvShowCasts = tvShowCastRepository.findTVShowCastByTVShowId(tvShowId);
        //RestTemplate restTemplate = new RestTemplate();
        //TVShowCastPersonDto tvShowCastPersonDto = restTemplate.getForObject(null, TVShowCastPersonDto.class);
        TVShowCastPersonDto tvShowCastPersonDummyDto = TVShowCastPersonDto
                .builder()
                .id(1L)
                .name("Dummy Name")
                .dateOfBirth(LocalDate.MAX)
                .role("Dummy Role")
                .photoUrl("https://dummyurl.com")
                .biographyDescription("Dummy Biography")
                .build();

        return tvShowCasts.stream()
                .map(tvShowCast -> new TVShowCastResponse(
                        tvShowCast.getId(),
                        tvShowCast.getTvShowId(),
                        tvShowCastPersonDummyDto,
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
                        .tvShowId(tvShowCastRequest.getTvShowId())
                        .personId(tvShowCastRequest.getPersonId())
                        .seasonNumber(tvShowCastRequest.getSeasonNumber())
                        .characterName(tvShowCastRequest.getCharacterName())
                        .roleType(tvShowCastRequest.getRoleType())
                        .build();
        tvShowCastRepository.save(tvShowCast);
    }

    @Override
    public TVShowCastResponse updateTVShowCast(Long castId, TVShowCastUpdateRequest tvShowCastUpdateRequest) {
        TVShowCastPersonDto tvShowCastPersonDummyDto = TVShowCastPersonDto
                .builder()
                .id(2L)
                .name("Dummy Name")
                .dateOfBirth(LocalDate.MAX)
                .role("Dummy Role")
                .photoUrl("https://dummyurl.com")
                .biographyDescription("Dummy Biography")
                .build();

        TVShowCast tvShowCast = tvShowCastRepository
                .findById(castId)
                .filter(filterTVShowCast -> !filterTVShowCast.getIsDeleted())
                .orElseThrow(() -> new EntityNotFoundException("TV Show Cast entry not found"));

        tvShowCast.setPersonId(tvShowCastUpdateRequest.getPersonId());
        tvShowCast.setSeasonNumber(tvShowCastUpdateRequest.getSeasonNumber());
        tvShowCast.setCharacterName(tvShowCastUpdateRequest.getCharacterName());
        tvShowCast.setRoleType(tvShowCastUpdateRequest.getRoleType());

        TVShowCast updatedCast = tvShowCastRepository.save(tvShowCast);

        return TVShowCastResponse.builder()
                .castId(updatedCast.getId())
                .tvShowId(updatedCast.getTvShowId())
                .tvShowCastPersonDto(tvShowCastPersonDummyDto)
                .characterName(updatedCast.getCharacterName())
                .seasonNumber(updatedCast.getSeasonNumber())
                .roleType(updatedCast.getRoleType())
                .build();
    }

    @Override
    public void deleteTVShowCastById(Long castId) {
        TVShowCast tvShowCast = tvShowCastRepository.findById(castId)
                .filter(cast -> cast.getIsDeleted() == null || !cast.getIsDeleted()) // Skip already deleted records
                .orElseThrow(() -> new EntityNotFoundException("TV Show Cast ID " + castId + " is unavailable"));

        tvShowCast.setIsDeleted(true);
        tvShowCast.setDeletedAt(Instant.now());

        tvShowCastRepository.save(tvShowCast);
    }
}
