package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.dto.TVShowCastPersonDto;
import com.imdbclone.tvshow.entity.TVShowCast;
import com.imdbclone.tvshow.repository.TVShowCastRepository;
import com.imdbclone.tvshow.service.api.ITVShowCastService;
import com.imdbclone.tvshow.web.request.TVShowCastRequest;
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
    public List<TVShowCastResponse> getCastByShowId(Long showId) {
        List<TVShowCast> tvShowCasts = tvShowCastRepository.findTVShowCastByShowId(showId);
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
                        tvShowCast.getShowId(),
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
                        .showId(tvShowCastRequest.getShowId())
                        .personId(tvShowCastRequest.getPersonId())
                        .seasonNumber(tvShowCastRequest.getSeasonNumber())
                        .characterName(tvShowCastRequest.getCharacterName())
                        .roleType(tvShowCastRequest.getRoleType())
                        .build();
        tvShowCastRepository.save(tvShowCast);
    }

    @Override
    public TVShowCastResponse updateTVShowCast(Long castId, TVShowCastRequest tvShowCastRequest) {
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
                .filter(filterTVShowCast -> !filterTVShowCast.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("TV Show Cast entry not found"));

        tvShowCast.setPersonId(tvShowCastRequest.getPersonId());
        tvShowCast.setSeasonNumber(tvShowCastRequest.getSeasonNumber());
        tvShowCast.setCharacterName(tvShowCastRequest.getCharacterName());
        tvShowCast.setRoleType(tvShowCastRequest.getRoleType());

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
        tvShowCast.setDeletedAt(Instant.now());

        tvShowCastRepository.save(tvShowCast);
    }
}
