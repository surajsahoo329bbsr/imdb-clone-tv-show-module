package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TVShowCast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TVShowCastRepository extends JpaRepository<TVShowCast, Long> {


}
