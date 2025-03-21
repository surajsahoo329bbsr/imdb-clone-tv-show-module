package com.imdbclone.tvshow.service;

import com.imdbclone.tvshow.entity.TVShow;
import com.imdbclone.tvshow.processor.CSVProcessor;
import com.imdbclone.tvshow.repository.TVShowGenreRepository;
import com.imdbclone.tvshow.repository.TVShowRepository;
import com.imdbclone.tvshow.service.client.AdminServiceClient;
import com.imdbclone.tvshow.service.implementation.TVShowServiceImpl;
import com.imdbclone.tvshow.web.response.TVShowResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.JWTUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TVShowServiceTest<T> {

    @Mock
    private TVShowRepository tvShowRepository;

    @Mock
    TVShowGenreRepository tvShowGenreRepository;

    @Mock
    CSVProcessor<T> csvProcessor;

    @Mock
    AdminServiceClient adminServiceClient;

    @Mock
    JWTUtils jwtUtils;

    @InjectMocks
    private TVShowServiceImpl<T> tvShowService;

    @Test
    void getTVShowByIdTest() {
        TVShow tvShow = TVShow
                .builder()
                .id(3L)
                .title("Friends 2")
                .language("Japanese")
                .description("A thrilling drama.")
                .build();

        when(tvShowRepository.findById(3L))
                .thenReturn(Optional.of(tvShow)); // Mock behavior

        TVShowResponse foundTVShowResponse = tvShowService.getTVShowById(3L);

        assertNotNull(foundTVShowResponse);
        assertEquals("ID matched", 3L, foundTVShowResponse.getId());
        assertEquals("Title Matched", "Friends 2", foundTVShowResponse.getTitle());
        assertEquals("Language Matched", "Japanese", foundTVShowResponse.getLanguage());
        assertEquals("Description Matched", "A thrilling drama.", foundTVShowResponse.getDescription());

        // Ensure repository method was called exactly once
        verify(tvShowRepository, times(1)).findById(3L);

    }

}
