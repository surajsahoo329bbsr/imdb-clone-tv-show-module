package com.imdbclone.tvshow.web.controller;

import com.imdbclone.tvshow.aspect.annotation.SetRequestAttributes;
import com.imdbclone.tvshow.service.api.ITVShowEpisodeService;
import com.imdbclone.tvshow.web.request.TVShowEpisodeRequest;
import com.imdbclone.tvshow.web.response.TVShowEpisodeResponse;
import com.imdbclone.tvshow.web.response.TVShowSeasonWithEpisodesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tv-shows")
@Tag(
        name = "TV Show Episodes",
        description = "APIs for managing TV show episodes, including retrieving, adding, updating, and deleting episodes."
)
public class TVShowEpisodeController {

    private final ITVShowEpisodeService tvShowEpisodeService;

    @Autowired
    public TVShowEpisodeController(ITVShowEpisodeService tvShowEpisodeService) {
        this.tvShowEpisodeService = tvShowEpisodeService;
    }

    @SetRequestAttributes
    @GetMapping(path = "/seasons/episodes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Retrieve a TV show episode by its ID",
            description = "Fetch details of a specific TV show episode using its unique identifier."
    )
    public ResponseEntity<?> getTVShowEpisodeById(@PathVariable(name = "id") Long episodeId) {
        TVShowEpisodeResponse tvShowEpisodeResponse = tvShowEpisodeService.getTVShowEpisodeById(episodeId);
        return new ResponseEntity<>(tvShowEpisodeResponse, HttpStatus.OK);
    }

    @SetRequestAttributes
    @GetMapping(path = "/seasons/{id}/episodes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Retrieve all episodes of a TV show season with pagination",
            description = "Fetch a paginated list of all episodes belonging to a specific season. "
                    + "By default, it returns page 1 with 10 episodes per page and sorts by oldest first. "
                    + "Set 'sortByLatestFirst' to true to get the latest episodes first."
    )
    public ResponseEntity<?> getTVShowEpisodesBySeasonId(@PathVariable(name = "id") Long seasonId, @RequestParam(defaultValue = "1") @Positive Integer pageNumber, @RequestParam(defaultValue = "10") @Positive Integer pageSize, @RequestParam(defaultValue = "false") boolean sortByLatestFirst) {
        TVShowSeasonWithEpisodesResponse tvShowSeasonWithEpisodesResponse = tvShowEpisodeService.getTVShowEpisodesBySeasonId(seasonId, pageNumber, pageSize, sortByLatestFirst);
        return new ResponseEntity<>(tvShowSeasonWithEpisodesResponse, HttpStatus.OK);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/seasons/episodes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Add a new episode to a TV show season",
            description = "Create a new episode for a TV show season by providing the episode details in the request body."
    )
    public ResponseEntity<?> addTVShowEpisode(@RequestBody TVShowEpisodeRequest tvShowEpisodeRequest) {
        tvShowEpisodeService.addTVShowEpisode(tvShowEpisodeRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/seasons/episodes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update details of a TV show episode",
            description = "Modify an existing episode's details by providing its unique ID and the updated information."
    )
    public ResponseEntity<?> updateTVShowEpisodeById(@PathVariable(name = "id") Long episodeId, @RequestBody TVShowEpisodeRequest tvShowEpisodeRequest) {
        TVShowEpisodeResponse tvShowEpisodeResponse = tvShowEpisodeService.updateTVShowEpisodeById(episodeId, tvShowEpisodeRequest);
        return new ResponseEntity<>(tvShowEpisodeResponse, HttpStatus.ACCEPTED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/seasons/episodes/{id}")
    @Operation(
            summary = "Delete a TV show episode by its ID",
            description = "Soft delete a TV show episode by marking it as deleted instead of permanently removing it."
    )
    public ResponseEntity<?> deleteTVShowEpisodeById(@PathVariable(name = "id") Long episodeId) {
        tvShowEpisodeService.deleteTVShowEpisodeById(episodeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
