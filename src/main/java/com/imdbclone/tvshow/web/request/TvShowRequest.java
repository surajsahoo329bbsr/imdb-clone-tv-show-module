package com.imdbclone.tvshow.web.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class TvShowRequest {

    @NotBlank(message = "TV Show Request's language cannot be blank")
    private String title;

    @Column(columnDefinition = "DATETIME", nullable = false)
    @NotBlank(message = "TV Show Request's release year cannot be blank")
    private LocalDateTime releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "TV Show Request's language cannot be blank")
    private String language;

    @Column(nullable = false)
    @Positive(message = "TV Show Request's season count must be a positive number")
    private Integer seasonsCount;

    @URL(message = "TV Show Request's poster URL must be valid")
    @Column(length = 2048)
    private String posterUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "TV Show's Admin ID cannot be blank")
    private Long adminId;

    private Boolean status;

}
