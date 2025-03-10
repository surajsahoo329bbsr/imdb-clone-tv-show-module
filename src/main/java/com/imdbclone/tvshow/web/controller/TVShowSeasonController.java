package com.imdbclone.tvshow.web.controller;

import com.imdbclone.tvshow.service.api.ITVShowSeasonService;
import com.imdbclone.tvshow.web.request.TVShowSeasonRequest;
import com.imdbclone.tvshow.web.response.TVShowSeasonResponse;
import com.imdbclone.tvshow.web.response.TVShowWithSeasonsResponse;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tv-shows")
public class TVShowSeasonController {

    @Autowired
    private ITVShowSeasonService tvShowSeasonService;

    @GetMapping(path = "/seasons/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTVShowSeasonByShowId(@PathVariable(name = "id") Long seasonId) {
        TVShowSeasonResponse tvShowSeasonResponse = tvShowSeasonService.getTVShowSeasonById(seasonId);
        return new ResponseEntity<>(tvShowSeasonResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/seasons", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTVShowSeasonsByShowId(@PathVariable(name = "id") Long showId, @RequestParam(defaultValue = "1") @Positive Integer pageNumber, @RequestParam(defaultValue = "10") @Positive Integer pageSize, @RequestParam(defaultValue = "false") boolean sortByLatestFirst) {
        TVShowWithSeasonsResponse tvShowWithSeasonsResponse = tvShowSeasonService.getTVShowSeasonsByShowId(showId, pageNumber, pageSize, sortByLatestFirst);
        return new ResponseEntity<>(tvShowWithSeasonsResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/seasons/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTVShowSeason(@RequestBody TVShowSeasonRequest tvShowSeasonRequest) {
        tvShowSeasonService.addTVShowSeason(tvShowSeasonRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(path = "/seasons/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTVShowSeasonById(@PathVariable(name = "id") Long seasonId, @RequestBody TVShowSeasonRequest tvShowSeasonRequest) {
        TVShowSeasonResponse tvShowSeasonResponse = tvShowSeasonService.updateTVShowSeasonById(seasonId, tvShowSeasonRequest);
        return new ResponseEntity<>(tvShowSeasonResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/seasons/{id}")
    public ResponseEntity<?> deleteTVShowSeasonById(@PathVariable(name = "id") Long seasonId) {
        tvShowSeasonService.deleteTVShowSeasonById(seasonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
