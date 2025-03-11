package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TVShowGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TVShowGenreRepository extends JpaRepository<TVShowGenre, Long> {

    @Query("SELECT t FROM TVShowGenre t WHERE t.showId = :showId")
    List<TVShowGenre> findTVShowGenreByShowId(Long showId);
}
