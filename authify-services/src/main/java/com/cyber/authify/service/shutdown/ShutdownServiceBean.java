package com.cyber.authify.service.shutdown;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShutdownServiceBean implements ShutdownService {

    @Autowired
    ApplicationContext context;

    @Async
    @Override
    public void shutdown() {
        log.info("Application shutdown initiated...");
        int exitCode = SpringApplication.exit(context);
        System.exit(exitCode);
    }
}
