package com.imdbclone.tvshow.configuration;

import com.imdbclone.tvshow.processor.CSVProcessor;
import com.imdbclone.tvshow.repository.TVShowCastRepository;
import com.imdbclone.tvshow.repository.TVShowRepository;
import com.imdbclone.tvshow.service.api.ITVShowCastService;
import com.imdbclone.tvshow.service.api.ITVShowService;
import com.imdbclone.tvshow.service.implementation.TVShowCastServiceImpl;
import com.imdbclone.tvshow.service.implementation.TVShowServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TVShowConfiguration<T> {

    @Bean
    public ITVShowService tvShowService(TVShowRepository tvShowRepository, CSVProcessor<T> csvProcessor){
        return new TVShowServiceImpl(tvShowRepository, csvProcessor);
    }

    @Bean
    public ITVShowCastService tvShowCastService(TVShowCastRepository tvShowCastRepository){
        return new TVShowCastServiceImpl(tvShowCastRepository);
    }

}
