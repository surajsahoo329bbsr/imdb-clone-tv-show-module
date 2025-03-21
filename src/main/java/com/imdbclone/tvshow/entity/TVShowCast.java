package com.imdbclone.tvshow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tv_show_cast")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TVShowCast extends Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "TV Show Cast's Show ID cannot be blank")
    private Long showId;

    @Column(nullable = false)
    @NotNull(message = "TV Show Cast's person ID cannot be blank")
    private Long personId;

    @Column(nullable = false)
    @Positive(message = "TV Show Cast's total seasons must be a positive number")
    private Integer seasonNumber;

    @Column(nullable = false)
    @NotBlank(message = "TV Show Cast's character name cannot be blank")
    private String characterName;

    private String roleType;
}