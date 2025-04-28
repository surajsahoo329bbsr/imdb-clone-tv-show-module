package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.dto.TVShowCastPersonDTO;
import com.imdbclone.tvshow.service.client.UserServiceClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
@Qualifier("userServiceClientImpl")
public class UserServiceClientImpl implements UserServiceClient {

    @Override
    public Map<Long, TVShowCastPersonDTO> getTVShowCastsByPersonIds(List<Long> personIds) {
        return Map.of(
                1L, TVShowCastPersonDTO.builder()
                        .id(1L)
                        .name("John Doe")
                        .dateOfBirth(LocalDate.of(1985, 5, 15))
                        .role("Lead Actor")
                        .photoUrl("https://example.com/photos/john_doe.jpg")
                        .biographyDescription("John is an experienced actor known for his roles in action films.")
                        .build(),

                2L, TVShowCastPersonDTO.builder()
                        .id(2L)
                        .name("Jane Smith")
                        .dateOfBirth(LocalDate.of(1990, 8, 25))
                        .role("Supporting Actress")
                        .photoUrl("https://example.com/photos/jane_smith.jpg")
                        .biographyDescription("Jane is a versatile actress with a passion for drama.")
                        .build(),

                3L, TVShowCastPersonDTO.builder()
                        .id(3L)
                        .name("Tom Harris")
                        .dateOfBirth(LocalDate.of(1978, 2, 10))
                        .role("Director")
                        .photoUrl("https://example.com/photos/tom_harris.jpg")
                        .biographyDescription("Tom is an award-winning director, known for his visionary work in TV shows.")
                        .build(),

                4L, TVShowCastPersonDTO.builder()
                        .id(4L)
                        .name("Alice Green")
                        .dateOfBirth(LocalDate.of(1992, 7, 30))
                        .role("Producer")
                        .photoUrl("https://example.com/photos/alice_green.jpg")
                        .biographyDescription("Alice is a talented producer known for her work in comedy series.")
                        .build(),

                5L, TVShowCastPersonDTO.builder()
                        .id(5L)
                        .name("Michael Brown")
                        .dateOfBirth(LocalDate.of(1980, 3, 12))
                        .role("Cinematographer")
                        .photoUrl("https://example.com/photos/michael_brown.jpg")
                        .biographyDescription("Michael is a skilled cinematographer with a passion for capturing dramatic visuals.")
                        .build()
        );
    }

    @Override
    public TVShowCastPersonDTO getTVShowCastByPersonId(Long personId) {
        return TVShowCastPersonDTO.builder()
                .id(1L)
                .name("John Doe")
                .dateOfBirth(LocalDate.of(1985, 5, 15))
                .role("Lead Actor")
                .photoUrl("https://example.com/photos/john_doe.jpg")
                .biographyDescription("John is an experienced actor known for his roles in action films.")
                .build();
    }
}
