package com.go.denys.selenium.automation.linkedin.jobs.scanner.service.scanner;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.JobAd;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.ScanResult;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.ScannerFilter;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.excel.JobAdsExcelWriter;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.service.filter.JobAdFilter;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.service.db.JobAdHistoryService;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.web.driver.scanner.BaseScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseScannerService<
        S extends BaseScanner<SF, A>,
        SF extends ScannerFilter,
        A extends JobAd,
        F extends JobAdFilter<SF, A>>  {

    private final F jobAdFilter;
    private final JobAdHistoryService jobAdHistoryService;

    protected BaseScannerService(F jobAdFilter, JobAdHistoryService jobAdHistoryService) {
        this.jobAdFilter = jobAdFilter;
        this.jobAdHistoryService = jobAdHistoryService;
    }

    private static final Logger logger = LoggerFactory.getLogger(BaseScannerService.class);

    public ScanResult<A> scan(SF scannerFilter) throws Exception {
        logger.info("Scanner start it's execution");
        ScanResult<A> result = new ScanResult<A>();

        S scanner = resolveScanner();
        scanner.setFilter(scannerFilter);
        List<A> ads = scanner.scan();

        result.setAdsFound(ads.size());

        if (!ads.isEmpty()) {
            ads = jobAdFilter.filter(ads, scannerFilter);
            logger.info("Count Ads after filter = {}", ads.size());

            if (!ads.isEmpty()) {
                if (scannerFilter.isPrevious()) {
                    jobAdHistoryService.save(new ArrayList<>(ads));
                }
                JobAdsExcelWriter excelWriter = new JobAdsExcelWriter();
                excelWriter.write(new ArrayList<>(ads), getFolderForExcelWriter());
            }
        }

        result.setAdsAfterFilter(ads.size());
        result.setAds(ads);

        return result;
    }

    protected abstract String getFolderForExcelWriter();

    protected abstract S resolveScanner();
}
