package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.TvShow;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TvShowRepository extends PagingAndSortingRepository<TvShow, Long> {

}
