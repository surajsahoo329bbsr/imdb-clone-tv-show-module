package com.imdbclone.tvshow.web.controller;

import com.imdbclone.tvshow.entity.TvShow;
import com.imdbclone.tvshow.service.api.ITvShowService;
import com.imdbclone.tvshow.web.request.TvShowRequest;
import com.imdbclone.tvshow.web.response.TvShowResponse;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/tv-shows")
public class TvShowController {

    @Autowired
    private ITvShowService tvShowService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTvShows(@RequestParam(defaultValue = "1") @Positive Integer pageNumber, @RequestParam(defaultValue = "10") @Positive Integer pageSize, @RequestParam(defaultValue = "false") Boolean sortByLatestFirst) {
        List<TvShowResponse> tvShows = tvShowService.getAllTvShows(pageNumber, pageSize, sortByLatestFirst);
        return new ResponseEntity<>(tvShows, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTvShowById(@PathVariable(name = "id") Long tvShowId) {
        TvShowResponse tvShowResponse = tvShowService.getTvShowById(tvShowId);
        return new ResponseEntity<>(tvShowResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTvShow(@RequestBody TvShowRequest tvShowRequest) {
        TvShow tvShow = tvShowService.addTvShow(tvShowRequest);
        return new ResponseEntity<>(tvShow, HttpStatus.CREATED);
    }

    @PostMapping(path = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadTvShows(MultipartFile tvShowsCsvFile) {
        tvShowService.uploadTvShows(tvShowsCsvFile);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTvShows(@PathVariable(name = "id") Long tvShowId, @RequestBody TvShowRequest tvShowRequest) {
        TvShow tvShow = tvShowService.updateTvShowById(tvShowId, tvShowRequest);
        return new ResponseEntity<>(tvShow, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTvShowById(@PathVariable(name = "id") Long tvShowId) {
        tvShowService.deleteTvShowById(tvShowId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}