package com.imdbclone.tvshow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class TvShowGenre {

    @Column(nullable = false)
    @NotBlank(message = "TV Show Genre's TV Show ID cannot be blank")
    private Long tvShowId;

    @Column(nullable = false)
    @NotBlank(message = "TV Show Genre's Genre ID cannot be blank")
    private Long genreId;
}