package com.imdbclone.tvshow.configuration;

import com.imdbclone.tvshow.filter.JwtAuthFilter;
import com.imdbclone.tvshow.processor.CSVProcessor;
import com.imdbclone.tvshow.repository.*;
import com.imdbclone.tvshow.service.api.ITVShowCastService;
import com.imdbclone.tvshow.service.api.ITVShowEpisodeService;
import com.imdbclone.tvshow.service.api.ITVShowSeasonService;
import com.imdbclone.tvshow.service.api.ITVShowService;
import com.imdbclone.tvshow.service.client.AdminServiceClient;
import com.imdbclone.tvshow.service.client.UserServiceClient;
import com.imdbclone.tvshow.service.implementation.TVShowCastServiceImpl;
import com.imdbclone.tvshow.service.implementation.TVShowEpisodeServiceImpl;
import com.imdbclone.tvshow.service.implementation.TVShowSeasonServiceImpl;
import com.imdbclone.tvshow.service.implementation.TVShowServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TVShowConfiguration<T> {

    @Bean
    public JwtAuthFilter jwtAuthFilter(AdminServiceClient adminServiceClient) {
        return new JwtAuthFilter(adminServiceClient);
    }

    @Bean
    public ITVShowService tvShowService(TVShowRepository tvShowRepository, TVShowGenreRepository tvShowGenreRepository, CSVProcessor<T> csvProcessor, AdminServiceClient adminServiceClient) {
        return new TVShowServiceImpl<>(tvShowRepository, tvShowGenreRepository, csvProcessor, adminServiceClient);
    }

    @Bean
    public ITVShowCastService tvShowCastService(TVShowCastRepository tvShowCastRepository, UserServiceClient userServiceClient) {
        return new TVShowCastServiceImpl(tvShowCastRepository, userServiceClient);
    }

    @Bean
    public ITVShowSeasonService tvShowSeasonService(TVShowSeasonRepository tvShowSeasonRepository, TVShowRepository tvShowRepository, TVShowGenreRepository tvShowGenreRepository, AdminServiceClient adminServiceClient) {
        return new TVShowSeasonServiceImpl(tvShowSeasonRepository, tvShowRepository, tvShowGenreRepository, adminServiceClient);
    }

    @Bean
    public ITVShowEpisodeService tvShowEpisodeService(TVShowEpisodeRepository tvShowEpisodeRepository, TVShowSeasonRepository tvShowSeasonRepository) {
        return new TVShowEpisodeServiceImpl(tvShowEpisodeRepository, tvShowSeasonRepository);
    }

}
