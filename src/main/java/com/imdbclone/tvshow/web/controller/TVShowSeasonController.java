package com.imdbclone.tvshow.web.controller;

import com.imdbclone.tvshow.aspect.annotation.SetRequestAttributes;
import com.imdbclone.tvshow.service.api.ITVShowSeasonService;
import com.imdbclone.tvshow.web.request.TVShowSeasonRequest;
import com.imdbclone.tvshow.web.response.TVShowSeasonResponse;
import com.imdbclone.tvshow.web.response.TVShowWithSeasonsResponse;
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
        name = "TV Show Seasons",
        description = "APIs for managing TV show seasons, including retrieving, adding, updating, and deleting seasons."
)
public class TVShowSeasonController {

    private final ITVShowSeasonService tvShowSeasonService;

    @Autowired
    public TVShowSeasonController(ITVShowSeasonService tvShowSeasonService) {
        this.tvShowSeasonService = tvShowSeasonService;
    }

    @SetRequestAttributes
    @GetMapping(path = "/seasons/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Retrieve a TV show season by its ID",
            description = "Fetch details of a specific TV show season using its unique identifier."
    )
    public ResponseEntity<?> getTVShowSeasonByShowId(@PathVariable(name = "id") Long seasonId) {
        TVShowSeasonResponse tvShowSeasonResponse = tvShowSeasonService.getTVShowSeasonById(seasonId);
        return new ResponseEntity<>(tvShowSeasonResponse, HttpStatus.OK);
    }

    @SetRequestAttributes
    @GetMapping(path = "/{id}/seasons", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Retrieve all seasons of a TV show with pagination",
            description = "Fetch a paginated list of all seasons belonging to a specific TV show. "
                    + "By default, it returns page 1 with 10 seasons per page and sorts by oldest first. "
                    + "Set 'sortByLatestFirst' to true to get the latest seasons first."
    )
    public ResponseEntity<?> getTVShowSeasonsByShowId(@PathVariable(name = "id") Long showId, @RequestParam(defaultValue = "1") @Positive Integer pageNumber, @RequestParam(defaultValue = "10") @Positive Integer pageSize, @RequestParam(defaultValue = "false") boolean sortByLatestFirst) {
        TVShowWithSeasonsResponse tvShowWithSeasonsResponse = tvShowSeasonService.getTVShowSeasonsByShowId(showId, pageNumber, pageSize, sortByLatestFirst);
        return new ResponseEntity<>(tvShowWithSeasonsResponse, HttpStatus.OK);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/seasons", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Add a new season to a TV show",
            description = "Create a new season for a TV show by providing the season details in the request body."
    )
    public ResponseEntity<?> addTVShowSeason(@RequestBody TVShowSeasonRequest tvShowSeasonRequest) {
        tvShowSeasonService.addTVShowSeason(tvShowSeasonRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/seasons/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update details of a TV show season",
            description = "Modify an existing season's details by providing its unique ID and the updated information."
    )
    public ResponseEntity<?> updateTVShowSeasonById(@PathVariable(name = "id") Long seasonId, @RequestBody TVShowSeasonRequest tvShowSeasonRequest) {
        TVShowSeasonResponse tvShowSeasonResponse = tvShowSeasonService.updateTVShowSeasonById(seasonId, tvShowSeasonRequest);
        return new ResponseEntity<>(tvShowSeasonResponse, HttpStatus.ACCEPTED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/seasons/{id}")
    @Operation(
            summary = "Delete a TV show season by its ID",
            description = "Soft delete a TV show season by marking it as deleted instead of permanently removing it."
    )
    public ResponseEntity<?> deleteTVShowSeasonById(@PathVariable(name = "id") Long seasonId) {
        tvShowSeasonService.deleteTVShowSeasonById(seasonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}