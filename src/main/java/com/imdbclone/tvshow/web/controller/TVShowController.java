package com.imdbclone.tvshow.web.controller;

import com.imdbclone.tvshow.annotation.SetRequestAttributes;
import com.imdbclone.tvshow.dto.TVShowWithGenreDTO;
import com.imdbclone.tvshow.service.api.ITVShowService;
import com.imdbclone.tvshow.web.request.TVShowRequest;
import com.imdbclone.tvshow.web.response.TVShowResponse;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/tv-shows")
public class TVShowController {

    @Autowired
    private ITVShowService tvShowService;

    @SetRequestAttributes
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTVShows(@RequestParam(defaultValue = "1") @Positive Integer pageNumber, @RequestParam(defaultValue = "10") @Positive Integer pageSize, @RequestParam(defaultValue = "false") boolean sortByLatestFirst) {
        List<TVShowWithGenreDTO> tvShows = tvShowService.getAllTVShows(pageNumber, pageSize, sortByLatestFirst);
        return new ResponseEntity<>(tvShows, HttpStatus.OK);
    }

    @SetRequestAttributes
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTVShowById(@PathVariable(name = "id") Long showId) {
        TVShowResponse tvShowResponse = tvShowService.getTVShowById(showId);
        return new ResponseEntity<>(tvShowResponse, HttpStatus.OK);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTVShow(@RequestBody TVShowRequest tvShowRequest) {
        tvShowService.addTVShow(tvShowRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadTVShows(@RequestParam("file") MultipartFile tvShowsCsvFile) {
        UUID uploadId = tvShowService.uploadTVShows(tvShowsCsvFile);
        return new ResponseEntity<>(uploadId, HttpStatus.ACCEPTED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTVShowById(@PathVariable(name = "id") Long id, @RequestBody TVShowRequest tvShowRequest) {
        TVShowResponse tvShowResponse = tvShowService.updateTVShowById(id, tvShowRequest);
        return new ResponseEntity<>(tvShowResponse, HttpStatus.ACCEPTED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteTVShowById(@PathVariable(name = "id") Long showId) {
        tvShowService.deleteTVShowById(showId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}