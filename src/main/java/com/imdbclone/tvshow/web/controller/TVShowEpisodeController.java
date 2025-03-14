package com.imdbclone.tvshow.web.controller;

import com.imdbclone.tvshow.service.api.ITVShowEpisodeService;
import com.imdbclone.tvshow.web.request.TVShowEpisodeRequest;
import com.imdbclone.tvshow.web.response.TVShowEpisodeResponse;
import com.imdbclone.tvshow.web.response.TVShowSeasonWithEpisodesResponse;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tv-shows")
public class TVShowEpisodeController {

    @Autowired
    private ITVShowEpisodeService tvShowEpisodeService;

    @GetMapping(path = "/seasons/episodes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTVShowEpisodeById(@PathVariable(name = "id") Long episodeId) {
        TVShowEpisodeResponse tvShowEpisodeResponse = tvShowEpisodeService.getTVShowEpisodeById(episodeId);
        return new ResponseEntity<>(tvShowEpisodeResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/seasons/{id}/episodes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTVShowEpisodesBySeasonId(@PathVariable(name = "id") Long seasonId, @RequestParam(defaultValue = "1") @Positive Integer pageNumber, @RequestParam(defaultValue = "10") @Positive Integer pageSize, @RequestParam(defaultValue = "false") boolean sortByLatestFirst) {
        TVShowSeasonWithEpisodesResponse tvShowSeasonWithEpisodesResponse = tvShowEpisodeService.getTVShowEpisodesBySeasonId(seasonId, pageNumber, pageSize, sortByLatestFirst);
        return new ResponseEntity<>(tvShowSeasonWithEpisodesResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/seasons/episodes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addTVShowEpisode(@RequestBody TVShowEpisodeRequest tvShowEpisodeRequest) {
        tvShowEpisodeService.addTVShowEpisode(tvShowEpisodeRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(path = "/seasons/episodes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTVShowEpisodeById(@PathVariable(name = "id") Long episodeId, @RequestBody TVShowEpisodeRequest tvShowEpisodeRequest) {
        TVShowEpisodeResponse tvShowEpisodeResponse = tvShowEpisodeService.updateTVShowEpisodeById(episodeId, tvShowEpisodeRequest);
        return new ResponseEntity<>(tvShowEpisodeResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/seasons/episodes/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTVShowEpisodeById(@PathVariable(name = "id") Long episodeId) {
        tvShowEpisodeService.deleteTVShowEpisodeById(episodeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
