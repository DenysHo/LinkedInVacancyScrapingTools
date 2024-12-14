package com.go.denys.selenium.automation.linkedin.jobs.scanner.service.scanner;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.JobAd;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.ScannerFilter;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.service.filter.JobAdFilter;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.service.db.JobAdHistoryService;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.web.driver.scanner.BaseScanner;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.web.driver.scanner.LinkedInScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkedInScannerService extends BaseScannerService<
        BaseScanner<ScannerFilter, JobAd>,
        ScannerFilter,
        JobAd,
        JobAdFilter<ScannerFilter, JobAd>> {

    @Autowired
    protected LinkedInScannerService(JobAdFilter<ScannerFilter, JobAd> jobAdFilter, JobAdHistoryService jobAdHistoryService) {
        super(jobAdFilter, jobAdHistoryService);
    }

    @Override
    protected String getFolderForExcelWriter() {
        return "C:\\Denys\\Documents\\jobs\\LinkedIn";
    }

    @Override
    protected LinkedInScanner resolveScanner() {
        return new LinkedInScanner();
    }
}
