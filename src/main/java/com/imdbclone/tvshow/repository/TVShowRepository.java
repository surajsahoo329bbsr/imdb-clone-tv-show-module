package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TVShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TVShowRepository extends PagingAndSortingRepository<TVShow, Long>, JpaRepository<TVShow, Long> {

    @Query(value = """
                 SELECT tv.id AS tvShowId,
                        tv.title AS title,
                        tg.genre_id AS genreId,
                        tv.release_year AS releaseYear,
                        tv.language AS language,
                        tv.seasons_count AS seasonsCount,
                        tv.score AS score,
                        tv.poster_url AS posterUrl,
                        tv.description AS description,
                        tv.status AS status,
                        tv.is_deleted AS isDeleted,
                 FROM tv_show tv
                 JOIN tv_show_genre tg ON tv.id = tg.show_id
                 WHERE tv.is_deleted = false
            """, nativeQuery = true)
    Page<Object[]> findTVShowsWithGenreById(Pageable pageable);
}
