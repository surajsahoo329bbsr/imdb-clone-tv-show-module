package com.imdbclone.tvshow.service.client;

import com.imdbclone.tvshow.dto.TVShowCastPersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @GetMapping(path = "/") //TODO path
    Map<Long, TVShowCastPersonDTO> getTVShowCastsByPersonIds(@RequestParam List<Long> personIds);

}
