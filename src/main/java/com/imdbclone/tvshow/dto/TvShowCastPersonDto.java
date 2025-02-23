package com.imdbclone.tvshow.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
@Setter
public class TvShowCastPersonDto {

    private Long id;

    private String name;

    private LocalDate dateOfBirth;

    private String role;

    private String photoUrl;

    private String biographyDescription;

}