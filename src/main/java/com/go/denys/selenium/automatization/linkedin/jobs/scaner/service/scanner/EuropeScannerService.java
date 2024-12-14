package com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.scanner;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.EuropaJobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.EuropaScannerFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.filter.EuropaJobAdFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.db.JobAdHistoryService;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.web.driver.scanner.EuropaScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EuropeScannerService extends BaseScannerService<EuropaScanner, EuropaScannerFilter, EuropaJobAd, EuropaJobAdFilter> {

    @Autowired
    public EuropeScannerService(EuropaJobAdFilter jobAdFilter, JobAdHistoryService jobAdHistoryService) {
        super(jobAdFilter, jobAdHistoryService);
    }

    @Override
    protected String getFolderForExcelWriter() {
        return "C:\\Denys\\Documents\\jobs\\Europa";
    }

    @Override
    protected EuropaScanner resolveScanner() {
        return new EuropaScanner();
    }
}
