package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TVShowSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TVShowSeasonRepository extends JpaRepository<TVShowSeason, Long> {
}
