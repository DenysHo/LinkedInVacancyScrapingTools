package com.go.denys.selenium.automation.linkedin.jobs.scanner.service.scanner;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.JobAd;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.LinkedInScannerFilter;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.ScanResult;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.service.db.JobAdHistoryService;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.service.filter.JobAdFilter;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.web.driver.scanner.BaseScanner;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.web.driver.scanner.LinkedInScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LinkedInScannerService extends BaseScannerService<
        BaseScanner<LinkedInScannerFilter, JobAd>,
        LinkedInScannerFilter,
        JobAd,
        JobAdFilter<LinkedInScannerFilter, JobAd>> {

    @Value("${linkedin.user.login}")
    private String linkedInUserLogin;
    @Value("${linkedin.user.password}")
    private String linkedInUserPassword;

    @Autowired
    protected LinkedInScannerService(JobAdFilter<LinkedInScannerFilter, JobAd> jobAdFilter, JobAdHistoryService jobAdHistoryService) {
        super(jobAdFilter, jobAdHistoryService);
    }

    @Override
    public ScanResult<JobAd> scan(LinkedInScannerFilter scannerFilter) throws Exception {
        scannerFilter.setLinkedInUserLogin(linkedInUserLogin);
        scannerFilter.setLinkedInUserPassword(linkedInUserPassword);
        return super.scan(scannerFilter);
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
