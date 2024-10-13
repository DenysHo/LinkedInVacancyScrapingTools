package com.go.denys.selenium.automatization.linkedin.jobs.scaner;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.ScannerFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.excel.JobAdsExcelWriter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.web.driver.scanner.LinkedInScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.TimeRangeSelector.DAY;
import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.util.JobAdUtil.print;
import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.util.JobAdUtil.printForTest;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws URISyntaxException {
        logger.info("Scanner start it's execution");

        ScannerFilter scannerFilter = ScannerFilter.builder()
                .timeRange(Optional.of(DAY))
                .keywords(Optional.of("Java Developer"))
                .location(Optional.of("Germany"))
                .uniqueness(true)
                .build();
        LinkedInScanner scanner = new LinkedInScanner(scannerFilter);
        List<JobAd> ads = scanner.scan();

        printForTest(ads.stream().limit(Long.MAX_VALUE).toList());

        JobAdFilter filter = new JobAdFilter(scannerFilter);
        List<JobAd> filtered = filter.filter(ads);

        logger.info("Count Ads after filter = {}", filtered.size());

        print(filtered);
        JobAdsExcelWriter excelWriter = new JobAdsExcelWriter();
        excelWriter.write(filtered);
    }
}
