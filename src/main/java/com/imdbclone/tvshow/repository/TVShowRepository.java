package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TVShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TVShowRepository extends PagingAndSortingRepository<TVShow, Long>, JpaRepository<TVShow, Long> {

}
