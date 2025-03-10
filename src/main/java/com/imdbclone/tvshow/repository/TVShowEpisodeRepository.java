package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TVShowEpisode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TVShowEpisodeRepository extends PagingAndSortingRepository<TVShowEpisode, Long>, JpaRepository<TVShowEpisode, Long> {

    @Query("SELECT t FROM TVShowEpisode t WHERE t.seasonId = :seasonId AND t.isDeleted = false")
    Page<TVShowEpisode> findTVShowEpisodesBySeasonId(@Param("seasonId") Long seasonId, Pageable pageable);

}
