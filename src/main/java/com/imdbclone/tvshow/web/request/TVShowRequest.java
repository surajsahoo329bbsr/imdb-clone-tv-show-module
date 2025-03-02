package com.imdbclone.tvshow.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class TVShowRequest {

    @NotBlank(message = "TV Show Request's language cannot be blank")
    private String title;

    @NotNull(message = "TV Show Request's release year cannot be blank")
    private LocalDateTime releaseYear;

    @NotBlank(message = "TV Show Request's language cannot be blank")
    private String language;

    @Positive(message = "TV Show Request's season count must be a positive number")
    private Integer seasonsCount;

    @URL(message = "TV Show Request's poster URL must be valid")
    private String posterUrl;

    private String description;

    @NotNull(message = "TV Show's Admin ID cannot be blank")
    private Long adminId;

    private Boolean status;

}
