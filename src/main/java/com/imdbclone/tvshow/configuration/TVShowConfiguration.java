package com.imdbclone.tvshow.configuration;

import com.imdbclone.tvshow.filter.JWTAuthFilter;
import com.imdbclone.tvshow.processor.CSVProcessor;
import com.imdbclone.tvshow.repository.*;
import com.imdbclone.tvshow.service.api.*;
import com.imdbclone.tvshow.service.client.AdminServiceClient;
import com.imdbclone.tvshow.service.client.UserServiceClient;
import com.imdbclone.tvshow.service.implementation.AdminServiceClientImpl;
import com.imdbclone.tvshow.service.implementation.UserServiceClientImpl;
import com.imdbclone.tvshow.service.implementation.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import util.JWTUtils;

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
    public JWTUtils jwtUtils() {
        return new JWTUtils(jwtSecretKey);
    }

    @Bean
    public JWTAuthFilter jwtAuthFilter(@Qualifier("adminServiceClientImpl") AdminServiceClient adminServiceClient) {
        return new JWTAuthFilter(jwtSecretKey, adminServiceClient);
    }

    @Bean
    public IJWTService jwtService(@Qualifier("adminServiceClientImpl") AdminServiceClient adminServiceClient) {
        return new JWTServiceImpl(adminServiceClient, passwordEncoder(), jwtSecretKey, username, email, hashedPassword);
    }

    /*@Bean
    public GlobalExceptionHandler globalExceptionHandler(IAppLogService appLogService, JWTUtils jwtUtils, HttpServletRequest httpServletRequest) {
        return new GlobalExceptionHandler(appLogService, jwtUtils, httpServletRequest);
    }

    @Bean
    public RequestAttributeAspect requestAttributeAspect(HttpServletRequest httpServletRequest) {
        return new RequestAttributeAspect(httpServletRequest);
    }*/

    @Bean
    public IAppLogService appLogService(AppLogRepository appLogRepository) {
        return new AppLogServiceImpl(appLogRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AdminServiceClient getAdminServiceClient() {
        return new AdminServiceClientImpl();
    }

    @Bean
    public UserServiceClient getUserServiceClient() {
        return new UserServiceClientImpl();
    }

    @Bean
    public ITVShowService tvShowService(TVShowRepository tvShowRepository, TVShowGenreRepository tvShowGenreRepository, CSVProcessor<T> csvProcessor, @Qualifier("adminServiceClientImpl") AdminServiceClient adminServiceClient, JWTUtils jwtUtils) {
        return new TVShowServiceImpl<>(tvShowRepository, tvShowGenreRepository, csvProcessor, adminServiceClient, jwtUtils);
    }

    @Bean
    public ITVShowCastService tvShowCastService(TVShowCastRepository tvShowCastRepository, @Qualifier("adminServiceClientImpl") UserServiceClient userServiceClient, JWTUtils jwtUtils) {
        return new TVShowCastServiceImpl(tvShowCastRepository, userServiceClient, jwtUtils);
    }

    @Bean
    public ITVShowSeasonService tvShowSeasonService(TVShowSeasonRepository tvShowSeasonRepository, TVShowRepository tvShowRepository, TVShowGenreRepository tvShowGenreRepository, @Qualifier("adminServiceClientImpl") AdminServiceClient adminServiceClient, JWTUtils jwtUtils) {
        return new TVShowSeasonServiceImpl(tvShowSeasonRepository, tvShowRepository, tvShowGenreRepository, adminServiceClient, jwtUtils);
    }

    @Bean
    public ITVShowEpisodeService tvShowEpisodeService(TVShowEpisodeRepository tvShowEpisodeRepository, TVShowSeasonRepository tvShowSeasonRepository, JWTUtils jwtUtils) {
        return new TVShowEpisodeServiceImpl(tvShowEpisodeRepository, tvShowSeasonRepository, jwtUtils);
    }

}
