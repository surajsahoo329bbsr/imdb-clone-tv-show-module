package com.imdbclone.tvshow.entity;

import com.imdbclone.tvshow.model.ActionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "app_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;

    private Long entityId;

    private String serviceMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionStatus actionStatus;

    @Column(nullable = false)
    private Integer httpStatusCode;

    @Column(columnDefinition = "TEXT")
    private String exceptionMessage;

    @Column(length = 45)
    private String ipAddress;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Long createdBy;

}
