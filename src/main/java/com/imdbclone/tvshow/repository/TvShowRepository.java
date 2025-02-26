package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvShowRepository extends PagingAndSortingRepository<TvShow, Long>, JpaRepository<TvShow, Long> {

}
