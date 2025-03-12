package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.dto.TVShowWithGenreDTO;
import com.imdbclone.tvshow.entity.TVShow;
import com.imdbclone.tvshow.exception.CustomException;
import com.imdbclone.tvshow.processor.CSVProcessor;
import com.imdbclone.tvshow.repository.TVShowGenreRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

public class TVShowServiceImpl<T> implements ITVShowService {

    private final TVShowRepository tvShowRepository;
    private final TVShowGenreRepository tvShowGenreRepository;
    private final CSVProcessor<T> csvProcessor;
    private final Map<Long, String> genres = Map.ofEntries(
            Map.entry(1L, "Action"),
            Map.entry(2L, "Adventure"),
            Map.entry(3L, "Animation"),
            Map.entry(4L, "Comedy"),
            Map.entry(5L, "Crime"),
            Map.entry(6L, "Documentary"),
            Map.entry(7L, "Drama"),
            Map.entry(8L, "Fantasy"),
            Map.entry(9L, "Historical"),
            Map.entry(10L, "Horror"),
            Map.entry(11L, "Mystery"),
            Map.entry(12L, "Romance"),
            Map.entry(13L, "Sci-Fi"),
            Map.entry(14L, "Thriller"),
            Map.entry(15L, "Supernatural"),
            Map.entry(16L, "Western"),
            Map.entry(17L, "Reality-TV"),
            Map.entry(18L, "Musical"),
            Map.entry(19L, "War"),
            Map.entry(20L, "Sports")
    );

    public TVShowServiceImpl(TVShowRepository tvShowRepository, TVShowGenreRepository tvShowGenreRepository, CSVProcessor<T> csvProcessor) {
        this.tvShowRepository = tvShowRepository;
        this.tvShowGenreRepository = tvShowGenreRepository;
        this.csvProcessor = csvProcessor;
    }

    @Override
    public List<TVShowWithGenreDTO> getAllTVShows(Integer pageNumber, Integer pageSize, boolean sortByLatestFirst) {
        try {
            Sort sort = sortByLatestFirst ? Sort.by("id").descending() :
                    Sort.by("id").ascending();
            Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
            Page<Object[]> page = tvShowRepository.findTVShowsWithGenreById(pageable);

            /*List<String> genreIds = page.stream()
                    .flatMap(tv -> Arrays.stream(((String) tv[2]).split(","))
                            .map(Long::parseLong))
                    .toList()
                    .stream()
                    .map(id -> genres.getOrDefault(id, "Unknown")) // Map genre ID to name
                    .toList();

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Create request entity
            HttpEntity<List<Long>> requestEntity = new HttpEntity<>(genreIds, headers);
            Map<Long, String> genres = restTemplate.exchange(
                            "https://dummyurl.com",
                            HttpMethod.POST,
                            requestEntity,
                            new ParameterizedTypeReference<HashMap<Long, List<String>>>() {
                            })
                    .getBody();*/

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
    public TVShowResponse getTVShowById(Long id) {

        List<String> genreList = tvShowGenreRepository.findTVShowGenreByShowId(id).stream()
                .map(t -> genres.getOrDefault(t.getGenreId(), "Unknown"))
                .toList();

        return tvShowRepository.findById(id)
                .map(tvShow -> new TVShowResponse(
                        tvShow.getId(),
                        tvShow.getTitle(),
                        genreList,
                        tvShow.getReleaseYear(),
                        tvShow.getLanguage(),
                        tvShow.getSeasonsCount(),
                        tvShow.getScore(),
                        tvShow.getPosterUrl(),
                        tvShow.getDescription(),
                        tvShow.isStatus()))
                .orElse(null);

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
                .adminId(tvShowRequest.getAdminId())
                .build();
        tvShowRepository.save(tvShow);
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
        Optional.ofNullable(tvShowRequest.getAdminId())
                .ifPresent(tvShow::setAdminId);
        Optional.of(tvShowRequest.isStatus())
                .ifPresent(tvShow::setStatus);

        return tvShowRepository.save(tvShow);
    }

    @Override
    @Transactional
    public void deleteTVShowById(Long id) {
        TVShow tvShow = tvShowRepository.findById(id)
                .filter(tv -> !tv.isDeleted()) // Skip already deleted records
                .orElseThrow(() -> new EntityNotFoundException("TV Show ID " + id + " is unavailable"));

        tvShow.setDeleted(true);
        tvShow.setDeletedAt(Instant.now());

        tvShowRepository.save(tvShow);
    }
}
