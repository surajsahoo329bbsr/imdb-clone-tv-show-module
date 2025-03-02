package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TVShowEpisode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TVShowEpisodeRepository extends JpaRepository<TVShowEpisode, Long> {
}
