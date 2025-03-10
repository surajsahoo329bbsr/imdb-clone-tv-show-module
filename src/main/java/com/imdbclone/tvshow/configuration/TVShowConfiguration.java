package com.imdbclone.tvshow.configuration;

import com.imdbclone.tvshow.processor.CSVProcessor;
import com.imdbclone.tvshow.repository.TVShowCastRepository;
import com.imdbclone.tvshow.repository.TVShowEpisodeRepository;
import com.imdbclone.tvshow.repository.TVShowRepository;
import com.imdbclone.tvshow.repository.TVShowSeasonRepository;
import com.imdbclone.tvshow.service.api.ITVShowCastService;
import com.imdbclone.tvshow.service.api.ITVShowEpisodeService;
import com.imdbclone.tvshow.service.api.ITVShowSeasonService;
import com.imdbclone.tvshow.service.api.ITVShowService;
import com.imdbclone.tvshow.service.implementation.TVShowCastServiceImpl;
import com.imdbclone.tvshow.service.implementation.TVShowEpisodeServiceImpl;
import com.imdbclone.tvshow.service.implementation.TVShowSeasonServiceImpl;
import com.imdbclone.tvshow.service.implementation.TVShowServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TVShowConfiguration<T> {

    @Bean
    public ITVShowService tvShowService(TVShowRepository tvShowRepository, CSVProcessor<T> csvProcessor) {
        return new TVShowServiceImpl<>(tvShowRepository, csvProcessor);
    }

    @Bean
    public ITVShowCastService tvShowCastService(TVShowCastRepository tvShowCastRepository) {
        return new TVShowCastServiceImpl(tvShowCastRepository);
    }

    @Bean
    public ITVShowSeasonService tvShowSeasonService(TVShowSeasonRepository tvShowSeasonRepository, TVShowRepository tvShowRepository) {
        return new TVShowSeasonServiceImpl(tvShowSeasonRepository, tvShowRepository);
    }

    @Bean
    public ITVShowEpisodeService tvShowEpisodeService(TVShowEpisodeRepository tvShowEpisodeRepository, TVShowSeasonRepository tvShowSeasonRepository) {
        return new TVShowEpisodeServiceImpl(tvShowEpisodeRepository, tvShowSeasonRepository);
    }

}
