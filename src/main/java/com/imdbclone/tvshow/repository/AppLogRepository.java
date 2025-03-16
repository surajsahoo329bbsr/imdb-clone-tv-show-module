package com.imdbclone.tvshow.repository;

import com.imdbclone.tvshow.entity.AppLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppLogRepository extends JpaRepository<AppLog, Long> {
}
