package com.imdbclone.tvshow.web.controller;

import com.imdbclone.tvshow.service.api.ITVShowCastService;
import com.imdbclone.tvshow.web.request.TVShowCastRequest;
import com.imdbclone.tvshow.web.request.TVShowCastUpdateRequest;
import com.imdbclone.tvshow.web.response.TVShowCastResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/tv-shows")
public class TVShowCastController {

    @Autowired
    private ITVShowCastService tvShowCastService;

    @GetMapping(path = "/{id}/cast", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTVShowCastsByTVShowId(@PathVariable(name = "id") Long tvShowId) {
        List<TVShowCastResponse> tvShowCastResponses = tvShowCastService.getCastByTVShowId(tvShowId);
        return new ResponseEntity<>(tvShowCastResponses, HttpStatus.OK);
    }

    @PostMapping(path = "/cast", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTVShowCast(@RequestBody TVShowCastRequest tvShowCastRequest) {
        tvShowCastService.addTVShowCast(tvShowCastRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(path = "/cast/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTVShowCastById(@PathVariable(name = "id") Long castId, @RequestBody TVShowCastUpdateRequest tvShowCastUpdateRequest) {
        TVShowCastResponse tvShowCastResponse = tvShowCastService.updateTVShowCast(castId, tvShowCastUpdateRequest);
        return new ResponseEntity<>(tvShowCastResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/cast/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTVShowCastById(@PathVariable(name = "id") Long castId) {
        tvShowCastService.deleteTVShowCastById(castId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
