package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TvShowCast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TvShowCastRepository extends JpaRepository<TvShowCast, Long> {
}
