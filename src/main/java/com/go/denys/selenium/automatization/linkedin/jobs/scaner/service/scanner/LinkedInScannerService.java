package com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.scanner;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.ScannerFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.filter.JobAdFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.db.JobAdHistoryService;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.web.driver.scanner.BaseScanner;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.web.driver.scanner.LinkedInScanner;
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
