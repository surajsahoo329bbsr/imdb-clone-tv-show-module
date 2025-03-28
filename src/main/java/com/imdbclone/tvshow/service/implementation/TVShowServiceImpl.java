package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.dto.TVShowWithGenreDTO;
import com.imdbclone.tvshow.entity.TVShow;
import com.imdbclone.tvshow.entity.TVShowGenre;
import com.imdbclone.tvshow.exception.CustomException;
import com.imdbclone.tvshow.processor.CSVProcessor;
import com.imdbclone.tvshow.repository.TVShowGenreRepository;
import com.imdbclone.tvshow.repository.TVShowRepository;
import com.imdbclone.tvshow.service.api.ITVShowService;
import com.imdbclone.tvshow.service.client.AdminServiceClient;
import com.imdbclone.tvshow.web.response.TVShowResponse;
import com.imdbclone.tvshow.web.request.TVShowRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.imdbclone.tvshow.util.JWTUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class TVShowServiceImpl<T> implements ITVShowService {

    private final TVShowRepository tvShowRepository;
    private final TVShowGenreRepository tvShowGenreRepository;
    private final CSVProcessor<T> csvProcessor;
    private final AdminServiceClient adminServiceClient;
    private final JWTUtils jwtUtils;

    public TVShowServiceImpl(TVShowRepository tvShowRepository, TVShowGenreRepository tvShowGenreRepository, CSVProcessor<T> csvProcessor, AdminServiceClient adminServiceClient, JWTUtils jwtUtils) {
        this.tvShowRepository = tvShowRepository;
        this.tvShowGenreRepository = tvShowGenreRepository;
        this.csvProcessor = csvProcessor;
        this.adminServiceClient = adminServiceClient;
        this.jwtUtils = jwtUtils;
    }

    @Override
    @Transactional
    public List<TVShowWithGenreDTO> getAllTVShows(Integer pageNumber, Integer pageSize, boolean sortByLatestFirst) {
        try {
            Sort sort = sortByLatestFirst ? Sort.by("id").descending() :
                    Sort.by("id").ascending();
            Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
            Page<Object[]> page = tvShowRepository.findTVShowsWithGenres(pageable);

            List<Long> genreIds = page.stream()
                    .flatMap(tv -> Arrays.stream(((String) tv[2]).split(","))
                            .map(Long::parseLong))
                    .toList();

            Map<Long, String> genres = adminServiceClient.getGenreNamesById(genreIds);

            return page.stream()
                    .map(tv -> new TVShowWithGenreDTO(
                            (Long) tv[0],
                            (String) tv[1],
                            Arrays.stream(((String) tv[2]).split(",")) // Get genre IDs for this specific row
                                    .map(Long::parseLong)
                                    .map(id -> genres.getOrDefault(id, "Unknown")) // Map genre ID to name
                                    .distinct() // Remove duplicates
                                    .toList(),
                            ((Timestamp) tv[3]).toLocalDateTime(),
                            (String) tv[4],
                            (Integer) tv[5],
                            (Float) tv[6],
                            (String) tv[7],
                            (String) tv[8],
                            (boolean) tv[9],
                            (boolean) tv[10]
                    ))
                    .filter(tv -> !tv.isDeleted())
                    .toList();

        } catch (IllegalArgumentException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage(), "Please check the arguments given");
        } catch (DataAccessException e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (NoSuchElementException e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "Please try to add some TV shows");
        }
    }

    @Override
    @Transactional
    public TVShowResponse getTVShowById(Long id) {

        TVShow tvShow = tvShowRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TV Show not found with id: " + id));

        List<TVShowGenre> tvShowGenres = tvShowGenreRepository.findTVShowGenreByShowId(id);
        if (tvShowGenres.isEmpty()) {
            return new TVShowResponse(
                    tvShow.getId(), tvShow.getTitle(), Collections.emptyList(),
                    tvShow.getReleaseYear(), tvShow.getLanguage(), tvShow.getSeasonsCount(),
                    tvShow.getScore(), tvShow.getPosterUrl(), tvShow.getDescription(),
                    tvShow.isStatus()
            );
        }

        List<Long> genreIds = tvShowGenres.stream().map(TVShowGenre::getGenreId).toList();
        Map<Long, String> genres = adminServiceClient.getGenreNamesById(genreIds);

        List<String> genreList = tvShowGenres.stream()
                .map(tvShowGenre -> genres.getOrDefault(tvShowGenre.getGenreId(), "Unknown"))
                .toList();

        return new TVShowResponse(
                tvShow.getId(), tvShow.getTitle(), genreList,
                tvShow.getReleaseYear(), tvShow.getLanguage(), tvShow.getSeasonsCount(),
                tvShow.getScore(), tvShow.getPosterUrl(), tvShow.getDescription(),
                tvShow.isStatus()
        );
    }

    @Override
    @Transactional
    public void addTVShow(TVShowRequest tvShowRequest) {
        TVShow tvShow = TVShow.builder()
                .title(tvShowRequest.getTitle())
                .releaseYear(tvShowRequest.getReleaseYear())
                .language(tvShowRequest.getLanguage())
                .seasonsCount(tvShowRequest.getSeasonsCount())
                .posterUrl(tvShowRequest.getPosterUrl())
                .description(tvShowRequest.getDescription())
                .status(tvShowRequest.isStatus())
                .createdBy(jwtUtils.getAdminIdFromJwt())
                .createdAt(LocalDateTime.now())
                .build();
        tvShowRepository.save(tvShow);
    }

    @Override
    @Transactional
    public UUID uploadTVShows(MultipartFile tvShowsCsvFile) {
        // Save list of movies

        return csvProcessor.processCsv(
                tvShowsCsvFile,
                record -> TVShow
                        .builder()
                        .title(record.get("title"))
                        .releaseYear(LocalDateTime.parse(record.get("releaseYear")))
                        .language(record.get("language"))
                        .seasonsCount(Integer.parseInt(record.get("seasonsCount")))
                        .posterUrl(record.get("posterUrl"))
                        .description(record.get("description"))
                        .status(Boolean.parseBoolean(record.get("status")))
                        .createdBy(jwtUtils.getAdminIdFromJwt())
                        .createdAt(LocalDateTime.now())
                        .build()
                ,
                tvShowRepository::saveAll // Save list of movies
        );
    }

    @Override
    @Transactional
    public TVShowResponse updateTVShowById(Long id, TVShowRequest tvShowRequest) {
        TVShow tvShow = tvShowRepository.findById(id)
                .filter(tv -> !tv.isDeleted()) // Skip deleted TV shows
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
        Optional.ofNullable(jwtUtils.getAdminIdFromJwt())
                .ifPresent(tvShow::setUpdatedBy);
        Optional.of(tvShowRequest.isStatus())
                .ifPresent(tvShow::setStatus);
        tvShow.setUpdatedAt(LocalDateTime.now());

        tvShowRepository.save(tvShow);

        List<TVShowGenre> tvShowGenres = tvShowGenreRepository.findTVShowGenreByShowId(id);
        List<Long> genreIds = tvShowGenres.stream().map(TVShowGenre::getGenreId).toList();
        Map<Long, String> genres = adminServiceClient.getGenreNamesById(genreIds);

        List<String> genreList = tvShowGenres.stream()
                .map(t -> genres.getOrDefault(t.getGenreId(), "Unknown"))
                .toList();

        return TVShowResponse.builder()
                .id(id)
                .title(tvShow.getTitle())
                .releaseYear(tvShow.getReleaseYear())
                .language(tvShow.getLanguage())
                .seasonsCount(tvShow.getSeasonsCount())
                .posterUrl(tvShow.getPosterUrl())
                .description(tvShow.getDescription())
                .status(tvShow.isStatus())
                .genres(genreList)
                .build();
    }

    @Override
    @Transactional
    public void deleteTVShowById(Long id) {
        TVShow tvShow = tvShowRepository.findById(id)
                .filter(tv -> !tv.isDeleted()) // Skip already deleted records
                .orElseThrow(() -> new EntityNotFoundException("TV Show ID " + id + " is unavailable"));

        tvShow.setDeleted(true);
        tvShow.setDeletedAt(LocalDateTime.now());
        tvShow.setDeletedBy(jwtUtils.getAdminIdFromJwt());

        tvShowRepository.save(tvShow);
    }
}
