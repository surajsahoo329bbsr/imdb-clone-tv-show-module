package com.imdbclone.tvshow.entity;
import java.io.Serializable;
import java.util.Objects;

public class TVShowGenreKey implements Serializable {
    private Long showId;
    private Long genreId;

    public TVShowGenreKey() {}

    public TVShowGenreKey(Long showId, Long genreId) {
        this.showId = showId;
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TVShowGenreKey that = (TVShowGenreKey) o;
        return Objects.equals(showId, that.showId) && Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showId, genreId);
    }
}
