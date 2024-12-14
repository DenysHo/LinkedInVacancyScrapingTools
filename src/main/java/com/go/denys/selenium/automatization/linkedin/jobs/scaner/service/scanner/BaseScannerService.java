package com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.scanner;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.ScannerFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.excel.JobAdsExcelWriter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.filter.JobAdFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.db.JobAdHistoryService;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.web.driver.scanner.BaseScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.util.JobAdUtil.print;
import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.util.JobAdUtil.printForTest;

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

    public List<A> scan(SF scannerFilter) throws URISyntaxException {
        logger.info("Scanner start it's execution");

        //todo
        S scanner = resolveScanner();
        scanner.setFilter(scannerFilter);
        List<A> ads = scanner.scan();

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

        return ads;
    }

    protected abstract String getFolderForExcelWriter();

    protected abstract S resolveScanner();
}
