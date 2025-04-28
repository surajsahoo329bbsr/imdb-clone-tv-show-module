package com.imdbclone.tvshow.respository;

import com.imdbclone.tvshow.entity.TVShowEpisode;
import com.imdbclone.tvshow.repository.TVShowEpisodeRepository;
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
public class TVShowEpisodeRepositoryTest {

    @Autowired
    private TVShowEpisodeRepository tvShowEpisodeRepository;

    @Test
    void shouldSaveAndRetrieveTVShowEpisode() {
        TVShowEpisode tvShowEpisode = TVShowEpisode.builder()
                .title("Shaktimaan")
                .description("Indian Superhero")
                .episodeNumber(1)
                .releaseDate(LocalDateTime.of(2001, 3, 20, 14, 30, 45))
                .score(7.2F)
                .createdAt(LocalDateTime.now())
                .createdBy(1L)
                .build();

        TVShowEpisode savedTVShowEpisode = tvShowEpisodeRepository.save(tvShowEpisode);
        TVShowEpisode retrievedTVShowEpisode = tvShowEpisodeRepository.findById(savedTVShowEpisode.getId()).orElse(null);

        assertThat(retrievedTVShowEpisode)
                .isNotNull()
                .extracting(TVShowEpisode::getId, TVShowEpisode::getSeasonId, TVShowEpisode::getEpisodeNumber, TVShowEpisode::getTitle,
                        TVShowEpisode::getDescription, TVShowEpisode::getDuration, TVShowEpisode::getReleaseDate, TVShowEpisode::getScore,
                        TVShowEpisode::getCreatedAt, TVShowEpisode::getCreatedBy)
                .containsExactly(
                        savedTVShowEpisode.getId(),
                        tvShowEpisode.getSeasonId(),
                        tvShowEpisode.getDescription(),
                        tvShowEpisode.getEpisodeNumber(),
                        tvShowEpisode.getTitle(),
                        tvShowEpisode.getDescription(),
                        tvShowEpisode.getDuration(),
                        tvShowEpisode.getReleaseDate(),
                        tvShowEpisode.getScore(),
                        tvShowEpisode.getCreatedAt(),
                        tvShowEpisode.getCreatedBy()
                );

    }

    @Test
    void shouldFindLatestTVShowEpisodesWithGenres() {
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<TVShowEpisode> page = tvShowEpisodeRepository.findTVShowEpisodesBySeasonId(1L, pageable);
        assertThat(page)
                .isNotNull();
    }

}
