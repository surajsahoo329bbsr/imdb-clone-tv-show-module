package com.imdbclone.tvshow.web.controller;

import com.imdbclone.tvshow.annotation.SetRequestAttributes;
import com.imdbclone.tvshow.dto.TVShowWithGenreDTO;
import com.imdbclone.tvshow.entity.TVShow;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTVShows(@RequestParam(defaultValue = "1") @Positive Integer pageNumber, @RequestParam(defaultValue = "10") @Positive Integer pageSize, @RequestParam(defaultValue = "false") boolean sortByLatestFirst) {
        List<TVShowWithGenreDTO> tvShows = tvShowService.getAllTVShows(pageNumber, pageSize, sortByLatestFirst);
        return new ResponseEntity<>(tvShows, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SetRequestAttributes
    public ResponseEntity<?> getTVShowById(@PathVariable(name = "id") Long showId) {
        System.out.println("Suraj: Inside Controller Method");
        TVShowResponse tvShowResponse = tvShowService.getTVShowById(showId);
        return new ResponseEntity<>(tvShowResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addTVShow(@RequestBody TVShowRequest tvShowRequest) {
        tvShowService.addTVShow(tvShowRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/upload/{adminId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadTVShows(@PathVariable(name = "adminId") Long adminId, @RequestParam("file") MultipartFile tvShowsCsvFile) {
        UUID uploadId = tvShowService.uploadTVShows(adminId, tvShowsCsvFile);
        return new ResponseEntity<>(uploadId, HttpStatus.ACCEPTED);
    }

    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTVShowById(@PathVariable(name = "id") Long id, @RequestBody TVShowRequest tvShowRequest) {
        TVShow tvShow = tvShowService.updateTVShowById(id, tvShowRequest);
        return new ResponseEntity<>(tvShow, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTVShowById(@PathVariable(name = "id") Long showId) {
        tvShowService.deleteTVShowById(showId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}