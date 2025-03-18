package com.imdbclone.tvshow.web.controller;

import com.imdbclone.tvshow.annotation.SetRequestAttributes;
import com.imdbclone.tvshow.service.api.ITVShowCastService;
import com.imdbclone.tvshow.web.request.TVShowCastRequest;
import com.imdbclone.tvshow.web.response.TVShowCastResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tv-shows")
public class TVShowCastController {

    @Autowired
    private ITVShowCastService tvShowCastService;

    @SetRequestAttributes
    @GetMapping(path = "/{id}/cast", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTVShowCastsByShowId(@PathVariable(name = "id") Long showId) {
        List<TVShowCastResponse> tvShowCastResponses = tvShowCastService.getCastsByShowId(showId);
        return new ResponseEntity<>(tvShowCastResponses, HttpStatus.OK);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/cast", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTVShowCast(@RequestBody TVShowCastRequest tvShowCastRequest) {
        tvShowCastService.addTVShowCast(tvShowCastRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/cast/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTVShowCastById(@PathVariable(name = "id") Long castId, @RequestBody TVShowCastRequest tvShowCastRequest) {
        TVShowCastResponse tvShowCastResponse = tvShowCastService.updateTVShowCast(castId, tvShowCastRequest);
        return new ResponseEntity<>(tvShowCastResponse, HttpStatus.ACCEPTED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/cast/{id}/delete")
    public ResponseEntity<?> deleteTVShowCastById(@PathVariable(name = "id") Long castId) {
        tvShowCastService.deleteTVShowCastById(castId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
