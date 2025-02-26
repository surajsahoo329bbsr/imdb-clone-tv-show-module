package com.imdbclone.tvshow.entity;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TvShowGenre {

    @Column(nullable = false)
    @NotBlank(message = "TV Show Genre's TV Show ID cannot be blank")
    public Long tvShowId;

    @Column(nullable = false)
    @NotBlank(message = "TV Show Genre's Genre ID cannot be blank")
    private Long genreId;
}