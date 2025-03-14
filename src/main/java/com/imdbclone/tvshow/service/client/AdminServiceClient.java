package com.imdbclone.tvshow.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ADMIN-SERVICE")
public interface AdminServiceClient {

    @GetMapping(path = "/admins/{id}")//TODO path
    Boolean isAdminValid(@PathVariable("id") Long adminId);

    @GetMapping(path = "/genres")
    Map<Long, String> getGenreNamesById(@RequestParam List<Long> genreIds);

}
