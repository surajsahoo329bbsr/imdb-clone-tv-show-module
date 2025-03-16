package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.entity.AppLog;
import com.imdbclone.tvshow.repository.AppLogRepository;
import com.imdbclone.tvshow.service.api.IAppLogService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class AppLogServiceImpl implements IAppLogService {

    private final AppLogRepository appLogRepository;

    public AppLogServiceImpl(AppLogRepository appLogRepository) {
        this.appLogRepository = appLogRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logAction(AppLog appLog) {
        try {
            appLogRepository.save(appLog);
        } catch (Exception e) {
            System.err.println("Logging failed: " + e.getMessage());
        }
    }
}
