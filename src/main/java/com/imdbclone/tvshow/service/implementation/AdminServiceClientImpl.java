package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.service.client.AdminServiceClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Qualifier("adminServiceClientImpl")
public class AdminServiceClientImpl implements AdminServiceClient {

    @Override
    public Boolean isAdminValid(Long adminId) {
        return true;
    }

    @Override
    public Map<Long, String> getGenreNamesById(List<Long> genreIds) {
        return Map.ofEntries(
                Map.entry(1L, "Action"),
                Map.entry(2L, "Adventure"),
                Map.entry(3L, "Animation"),
                Map.entry(4L, "Comedy"),
                Map.entry(5L, "Crime"),
                Map.entry(6L, "Documentary"),
                Map.entry(7L, "Drama"),
                Map.entry(8L, "Fantasy"),
                Map.entry(9L, "Historical"),
                Map.entry(10L, "Horror"),
                Map.entry(11L, "Mystery"),
                Map.entry(12L, "Romance"),
                Map.entry(13L, "Sci-Fi"),
                Map.entry(14L, "Thriller"),
                Map.entry(15L, "Supernatural"),
                Map.entry(16L, "Western"),
                Map.entry(17L, "Reality-TV"),
                Map.entry(18L, "Musical"),
                Map.entry(19L, "War"),
                Map.entry(20L, "Sports"));
    }

    @Override
    public Long authenticateAdminAndFetchId(String username, String email, String password) {
        return 1L;
    }
}
