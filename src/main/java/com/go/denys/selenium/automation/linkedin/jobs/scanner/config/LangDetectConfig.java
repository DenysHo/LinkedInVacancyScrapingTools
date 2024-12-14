package com.go.denys.selenium.automation.linkedin.jobs.scanner.config;

import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LangDetectConfig {

    private static final Logger logger = LoggerFactory.getLogger(LangDetectConfig.class);

    @PostConstruct
    public void init() {
        try {
            // Инициализация DetectorFactory с загрузкой профилей
            DetectorFactory.loadProfile("src/main/resources/profiles");
            logger.info("Language profiles successfully loaded.");
        } catch (LangDetectException e) {
            logger.error("An error occurred while loading language profiles: {}", e.getMessage(), e);
        }
    }
}
