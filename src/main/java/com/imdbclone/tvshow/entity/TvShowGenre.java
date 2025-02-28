package com.imdbclone.tvshow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(TvShowGenreKey.class)
public class TvShowGenre {

    @Id
    @Column(nullable = false)
    @NotBlank(message = "TV Show Genre's TV Show ID cannot be blank")
    public Long tvShowId;

    @Id
    @Column(nullable = false)
    @NotBlank(message = "TV Show Genre's Genre ID cannot be blank")
    private Long genreId;
}