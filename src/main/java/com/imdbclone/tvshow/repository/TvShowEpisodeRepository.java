package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TvShowEpisode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvShowEpisodeRepository extends JpaRepository<TvShowEpisode, Long> {
}
