package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TvShowSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvShowSeasonRepository extends JpaRepository<TvShowSeason, Long> {
}
