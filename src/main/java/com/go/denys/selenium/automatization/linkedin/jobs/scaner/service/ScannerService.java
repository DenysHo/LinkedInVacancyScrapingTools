package com.go.denys.selenium.automatization.linkedin.jobs.scaner.service;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.web.driver.scanner.LinkedInScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScannerService {

    private final LinkedInScanner scanner;

    @Autowired
    public ScannerService(LinkedInScanner scanner) {
        this.scanner = scanner;
    }

    public List<JobAd> scan() {
        List<JobAd> jobs = new ArrayList<JobAd>();
        try {
            List<JobAd> scan = scanner.scan();
        } catch (URISyntaxException e) {
            //todo
            throw new RuntimeException(e);
        }
        return jobs;
    }
}
