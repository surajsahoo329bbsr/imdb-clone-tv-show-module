package com.imdbclone.tvshow.web.controller;

import com.imdbclone.tvshow.aspect.annotation.SetRequestAttributes;
import com.imdbclone.tvshow.dto.TVShowWithGenreDTO;
import com.imdbclone.tvshow.service.api.ITVShowService;
import com.imdbclone.tvshow.web.request.TVShowRequest;
import com.imdbclone.tvshow.web.response.TVShowResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "TV Shows",
        description = "APIs for managing TV shows, including retrieving, adding, updating, deleting, and bulk uploading TV shows."
)
public class TVShowController {

    private final ITVShowService tvShowService;

    @Autowired
    public TVShowController(ITVShowService tvShowService) {
        this.tvShowService = tvShowService;
    }

    @SetRequestAttributes
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Retrieve all TV shows with pagination and sorting",
            description = "Fetch a paginated list of TV shows. You can specify the page number, page size, and sorting order. "
                    + "By default, it returns page 1 with 10 items per page and sorts by oldest first. "
                    + "Set 'sortByLatestFirst' to true to get the latest TV shows first."
    )
    public ResponseEntity<?> getAllTVShows(@RequestParam(defaultValue = "1") @Positive Integer pageNumber, @RequestParam(defaultValue = "10") @Positive Integer pageSize, @RequestParam(defaultValue = "false") boolean sortByLatestFirst) {
        List<TVShowWithGenreDTO> tvShows = tvShowService.getAllTVShows(pageNumber, pageSize, sortByLatestFirst);
        return new ResponseEntity<>(tvShows, HttpStatus.OK);
    }

    @SetRequestAttributes
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Retrieve a TV show by its ID",
            description = "Fetch details of a specific TV show using its unique identifier."
    )
    public ResponseEntity<?> getTVShowById(@PathVariable(name = "id") Long showId) {
        TVShowResponse tvShowResponse = tvShowService.getTVShowById(showId);
        return new ResponseEntity<>(tvShowResponse, HttpStatus.OK);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Add a new TV show",
            description = "Create a new TV show by providing its details in the request body."
    )
    public ResponseEntity<?> addTVShow(@RequestBody TVShowRequest tvShowRequest) {
        tvShowService.addTVShow(tvShowRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Bulk upload TV shows from CSV",
            description = "Upload a CSV file containing multiple TV shows to add them in bulk."
    )
    public ResponseEntity<?> uploadTVShows(@RequestParam("file") MultipartFile tvShowsCsvFile) {
        UUID uploadId = tvShowService.uploadTVShows(tvShowsCsvFile);
        return new ResponseEntity<>(uploadId, HttpStatus.ACCEPTED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update an existing TV show",
            description = "Modify details of an existing TV show by providing its ID and the updated information."
    )
    public ResponseEntity<?> updateTVShowById(@PathVariable(name = "id") Long id, @RequestBody TVShowRequest tvShowRequest) {
        TVShowResponse tvShowResponse = tvShowService.updateTVShowById(id, tvShowRequest);
        return new ResponseEntity<>(tvShowResponse, HttpStatus.ACCEPTED);
    }

    @SetRequestAttributes
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    @Operation(
            summary = "Delete a TV show by its ID",
            description = "Soft delete a TV show by marking it as deleted instead of permanently removing it."
    )
    public ResponseEntity<?> deleteTVShowById(@PathVariable(name = "id") Long showId) {
        tvShowService.deleteTVShowById(showId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}