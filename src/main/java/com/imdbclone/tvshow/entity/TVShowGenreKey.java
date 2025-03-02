package com.imdbclone.tvshow.entity;
import java.io.Serializable;
import java.util.Objects;

public class TVShowGenreKey implements Serializable {
    private Long tvShowId;
    private Long genreId;

    public TVShowGenreKey() {}

    public TVShowGenreKey(Long tvShowId, Long genreId) {
        this.tvShowId = tvShowId;
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TVShowGenreKey that = (TVShowGenreKey) o;
        return Objects.equals(tvShowId, that.tvShowId) && Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tvShowId, genreId);
    }
}
