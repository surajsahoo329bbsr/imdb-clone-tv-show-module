package com.imdbclone.tvshow.web.controller;

import com.imdbclone.tvshow.aspect.annotation.SetRequestAttributes;
import com.imdbclone.tvshow.service.api.ITVShowCastService;
import com.imdbclone.tvshow.web.request.TVShowCastRequest;
import com.imdbclone.tvshow.web.response.TVShowCastResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tv-shows")
@Tag(
        name = "TV Show Cast",
        description = "APIs for managing TV show cast members, including retrieving, adding, updating, and deleting cast details."
)
public class TVShowCastController {

    private final ITVShowCastService tvShowCastService;

    @Autowired
    public TVShowCastController(ITVShowCastService tvShowCastService) {
        this.tvShowCastService = tvShowCastService;
    }

    @SetRequestAttributes
    @GetMapping(path = "/{id}/cast", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Retrieve all cast members of a TV show",
            description = "Fetch the list of all cast members associated with a specific TV show by providing its unique ID."
    )
    public ResponseEntity<?> getTVShowCastsByShowId(@PathVariable(name = "id") Long showId) {
        List<TVShowCastResponse> tvShowCastResponses = tvShowCastService.getCastsByShowId(showId);
        return new ResponseEntity<>(tvShowCastResponses, HttpStatus.OK);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/cast/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update details of a TV show cast member",
            description = "Modify an existing cast member's details by providing their unique ID and the updated information."
    )
    public ResponseEntity<?> updateTVShowCastById(@PathVariable(name = "id") Long castId, @RequestBody TVShowCastRequest tvShowCastRequest) {
        TVShowCastResponse tvShowCastResponse = tvShowCastService.updateTVShowCast(castId, tvShowCastRequest);
        return new ResponseEntity<>(tvShowCastResponse, HttpStatus.ACCEPTED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/cast", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Add a new cast member to a TV show",
            description = "Create a new cast entry for a TV show by providing the actor's details and their role in the request body."
    )
    public ResponseEntity<?> addTVShowCast(@RequestBody TVShowCastRequest tvShowCastRequest) {
        tvShowCastService.addTVShowCast(tvShowCastRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/cast/{id}/delete")
    @Operation(
            summary = "Delete a cast member from a TV show",
            description = "Remove a cast member from a TV show by providing their unique ID. "
                    + "This operation performs a soft delete instead of permanently removing the record."
    )
    public ResponseEntity<?> deleteTVShowCastById(@PathVariable(name = "id") Long castId) {
        tvShowCastService.deleteTVShowCastById(castId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
