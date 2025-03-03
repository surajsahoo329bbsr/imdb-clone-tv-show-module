package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.entity.TVShow;
import com.imdbclone.tvshow.processor.CSVProcessor;
import com.imdbclone.tvshow.repository.TVShowRepository;
import com.imdbclone.tvshow.service.api.ITVShowService;
import com.imdbclone.tvshow.web.response.TVShowResponse;
import com.imdbclone.tvshow.web.request.TVShowRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class TVShowServiceImpl<T> implements ITVShowService {

    private final TVShowRepository tvShowRepository;
    private final CSVProcessor<T> csvProcessor;


    public TVShowServiceImpl(TVShowRepository tvShowRepository, CSVProcessor<T> csvProcessor) {
        this.tvShowRepository = tvShowRepository;
        this.csvProcessor = csvProcessor;
    }

    @Override
    public List<TVShowResponse> getAllTVShows(Integer pageNumber, Integer pageSize, Boolean sortByLatestFirst) {
        try {
            Sort sort = sortByLatestFirst ? Sort.by("id").descending() :
                    Sort.by("id").ascending();
            Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
            Page<TVShow> page = tvShowRepository.findAll(pageable);
            return page.stream()
                    .map(tvShow -> new TVShowResponse(
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
    public TVShowResponse getTVShowById(Long id) {
        return tvShowRepository.findById(id)
                .map(tvShow -> new TVShowResponse(
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
    @Transactional
    public TVShow addTVShow(TVShowRequest tvShowRequest) {
        TVShow tvShow = TVShow.builder()
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
    @Transactional
    public UUID uploadTVShows(Long adminId, MultipartFile tvShowsCsvFile) {
        // Save list of movies
        return csvProcessor.processCsv(
                tvShowsCsvFile,
                record -> TVShow.builder()
                        .title(record.get("title"))
                        .releaseYear(LocalDateTime.parse(record.get("releaseYear")))
                        .language(record.get("language"))
                        .seasonsCount(Integer.parseInt(record.get("seasonsCount")))
                        .posterUrl(record.get("posterUrl"))
                        .description(record.get("description"))
                        .status(Boolean.parseBoolean(record.get("status")))
                        .adminId(adminId)
                        .build(),
                tvShowRepository::saveAll // Save list of movies
        );
    }

    @Override
    @Transactional
    public TVShow updateTVShowById(Long id, TVShowRequest tvShowRequest) {
        TVShow tvShow = tvShowRepository.findById(id)
                .filter(tv -> tv.getIsDeleted() == null || !tv.getIsDeleted()) // Skip deleted TV shows
                .orElseThrow(() -> new EntityNotFoundException("TV Show not found with ID: " + id));

        // Update only non-null values
        Optional.ofNullable(tvShowRequest.getTitle())
                .ifPresent(tvShow::setTitle);
        Optional.ofNullable(tvShowRequest.getReleaseYear())
                .ifPresent(tvShow::setReleaseYear);
        Optional.ofNullable(tvShowRequest.getLanguage())
                .ifPresent(tvShow::setLanguage);
        Optional.ofNullable(tvShowRequest.getSeasonsCount())
                .ifPresent(tvShow::setSeasonsCount);
        Optional.ofNullable(tvShowRequest.getPosterUrl())
                .ifPresent(tvShow::setPosterUrl);
        Optional.ofNullable(tvShowRequest.getDescription())
                .ifPresent(tvShow::setDescription);
        Optional.ofNullable(tvShowRequest.getAdminId())
                .ifPresent(tvShow::setAdminId);
        Optional.ofNullable(tvShowRequest.getStatus())
                .ifPresent(tvShow::setStatus);

        return tvShowRepository.save(tvShow);
    }

    @Override
    @Transactional
    public void deleteTVShowById(Long id) {
        TVShow tvShow = tvShowRepository.findById(id)
                .filter(tv -> tv.getIsDeleted() == null || !tv.getIsDeleted()) // Skip already deleted records
                .orElseThrow(() -> new EntityNotFoundException("TV Show ID " + id + " is unavailable"));

        tvShow.setIsDeleted(true);
        tvShow.setDeletedAt(Instant.now());

        tvShowRepository.save(tvShow);
    }
}
