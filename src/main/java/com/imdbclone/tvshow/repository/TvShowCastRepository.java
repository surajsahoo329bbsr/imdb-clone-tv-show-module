package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TvShowCast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvShowCastRepository extends JpaRepository<TvShowCast, Long> {
}
