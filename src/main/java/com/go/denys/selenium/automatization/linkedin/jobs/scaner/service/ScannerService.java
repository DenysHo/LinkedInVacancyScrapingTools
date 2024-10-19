package com.go.denys.selenium.automatization.linkedin.jobs.scaner.service;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.JobAdFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.ScannerFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.excel.JobAdsExcelWriter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.web.driver.scanner.LinkedInScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.util.JobAdUtil.print;
import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.util.JobAdUtil.printForTest;

@Service
public class ScannerService {

    private final JobAdFilter jobAdFilter;
    private final JobAdHistoryService jobAdHistoryService;

    @Autowired
    public ScannerService(JobAdFilter jobAdFilter, JobAdHistoryService jobAdHistoryService) {
        this.jobAdFilter = jobAdFilter;
        this.jobAdHistoryService = jobAdHistoryService;
    }

    private static final Logger logger = LoggerFactory.getLogger(ScannerService.class);

    public List<JobAd> scan(ScannerFilter scannerFilter) throws URISyntaxException {
        logger.info("Scanner start it's execution");


        LinkedInScanner scanner = new LinkedInScanner(scannerFilter);
        List<JobAd> ads = scanner.scan();

        //printForTest(ads.stream().limit(Long.MAX_VALUE).toList());

        //todo
        ads = jobAdFilter.filter(ads, scannerFilter);

        jobAdHistoryService.save(ads);

        logger.info("Count Ads after filter = {}", ads.size());

        print(ads);
        JobAdsExcelWriter excelWriter = new JobAdsExcelWriter();
        excelWriter.write(ads);
        return ads;
    }
}
