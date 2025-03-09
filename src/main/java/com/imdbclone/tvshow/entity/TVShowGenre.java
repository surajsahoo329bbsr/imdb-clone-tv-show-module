package com.imdbclone.tvshow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tv_show_genre")
@IdClass(TVShowGenreKey.class)
public class TVShowGenre {

    @Id
    @Column(nullable = false)
    @NotNull(message = "TV Show Genre's TV Show ID cannot be blank")
    public Long showId;

    @Id
    @Column(nullable = false)
    @NotNull(message = "TV Show Genre's Genre ID cannot be blank")
    private Long genreId;
}