package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TVShowSeason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TVShowSeasonRepository extends PagingAndSortingRepository<TVShowSeason, Long>, JpaRepository<TVShowSeason, Long> {

    @Query("SELECT t FROM TVShowSeason t WHERE t.showId = :showId AND t.isDeleted = false")
    Page<TVShowSeason> findTVShowSeasonsByShowId(@Param("showId") Long showId, Pageable pageable);
}
