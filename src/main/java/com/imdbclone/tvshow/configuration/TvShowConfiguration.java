package com.imdbclone.tvshow.configuration;

import com.imdbclone.tvshow.repository.TvShowCastRepository;
import com.imdbclone.tvshow.repository.TvShowRepository;
import com.imdbclone.tvshow.service.api.ITvShowCastService;
import com.imdbclone.tvshow.service.api.ITvShowService;
import com.imdbclone.tvshow.service.implementation.TvShowCastServiceImpl;
import com.imdbclone.tvshow.service.implementation.TvShowServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TvShowConfiguration {

    @Bean
    public ITvShowService tvShowService(TvShowRepository tvShowRepository){
        return new TvShowServiceImpl(tvShowRepository);
    }

    @Bean
    public ITvShowCastService tvShowCastService(TvShowCastRepository tvShowCastRepository){
        return new TvShowCastServiceImpl(tvShowCastRepository);
    }

}
