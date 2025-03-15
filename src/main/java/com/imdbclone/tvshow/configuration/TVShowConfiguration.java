package com.imdbclone.tvshow.configuration;

import com.imdbclone.tvshow.filter.JwtAuthFilter;
import com.imdbclone.tvshow.processor.CSVProcessor;
import com.imdbclone.tvshow.repository.*;
import com.imdbclone.tvshow.service.api.*;
import com.imdbclone.tvshow.service.client.AdminServiceClient;
import com.imdbclone.tvshow.service.client.UserServiceClient;
import com.imdbclone.tvshow.service.implementation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class TVShowConfiguration<T> {

    @Value("${spring.jwt.secret}")
    private String jwtSecretKey;

    @Value("${spring.jwt.username}")
    private String username;

    @Value("${spring.jwt.email}")
    private String email;

    @Value("${spring.jwt.hash-password}")
    private String hashedPassword;

    @Bean
    public JwtAuthFilter jwtAuthFilter(AdminServiceClient adminServiceClient) {
        return new JwtAuthFilter(jwtSecretKey, adminServiceClient);
    }

    @Bean
    public IJwtService jwtService(AdminServiceClient adminServiceClient) {
        return new JwtServiceImpl(adminServiceClient, passwordEncoder(), jwtSecretKey, username, email, hashedPassword);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
