package com.imdbclone.tvshow.respository;

import com.imdbclone.tvshow.entity.TVShow;
import com.imdbclone.tvshow.repository.TVShowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;


import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("dev")
public class TVShowRepositoryTest {

    @Autowired
    private TVShowRepository tvShowRepository;

    @Test
    void shouldSaveAndRetrieveTVShow() {
        TVShow tvShow = TVShow.builder()
                .title("Sooryavansham")
                .description("Heera Thakur")
                .language("Hindi")
                .posterUrl("https://setmax.com/images/ipl/2")
                .seasonsCount(Integer.MAX_VALUE)
                .releaseYear(LocalDateTime.of(2025, 3, 20, 14, 30, 45))
                .score(10.0F)
                .status(false)
                .createdAt(LocalDateTime.now())
                .createdBy(1L)
                .build();

        TVShow savedTVShow = tvShowRepository.save(tvShow);
        TVShow retrievedTVShow = tvShowRepository.findById(savedTVShow.getId()).orElse(null);

        assertThat(retrievedTVShow)
                .isNotNull()
                .extracting(TVShow::getId, TVShow::getTitle, TVShow::getDescription, TVShow::getLanguage,
                        TVShow::getPosterUrl, TVShow::getSeasonsCount, TVShow::getReleaseYear, TVShow::getScore,
                        TVShow::isStatus, TVShow::getCreatedAt, TVShow::getCreatedBy)
                .containsExactly(
                        savedTVShow.getId(),
                        tvShow.getTitle(),
                        tvShow.getDescription(),
                        tvShow.getLanguage(),
                        tvShow.getPosterUrl(),
                        tvShow.getSeasonsCount(),
                        tvShow.getReleaseYear(),
                        tvShow.getScore(),
                        tvShow.isStatus(),
                        tvShow.getCreatedAt(),
                        tvShow.getCreatedBy()
                );

    }

    @Test
    void shouldFindLatestTVShowsWithGenres() {
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<Object[]> page = tvShowRepository.findTVShowsWithGenres(pageable);
        assertThat(page)
                .isNotNull();
    }

}
