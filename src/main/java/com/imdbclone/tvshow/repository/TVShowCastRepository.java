package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TVShowCast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TVShowCastRepository extends JpaRepository<TVShowCast, Long> {

    //Using JPQL instead of native Hibernate Queries as it has control of all POJO entities and is independent of any Db tables
    @Query("SELECT t FROM TVShowCast t WHERE t.showId = :showId AND t.isDeleted = false")
    List<TVShowCast> findTVShowCastByShowId(@Param("showId") Long showId);
}
