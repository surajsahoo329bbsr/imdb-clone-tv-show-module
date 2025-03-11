package com.imdbclone.tvshow.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class TVShowCastPersonDTO {

    private Long id;

    private String name;

    private LocalDate dateOfBirth;

    private String role;

    private String photoUrl;

    private String biographyDescription;

}