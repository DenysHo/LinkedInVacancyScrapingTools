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

public class Main {

    private static final String PATTERN = "%s, %s, %s, %s;";

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws URISyntaxException {
        logger.info("Scanner start it's execution");

        ScannerFilter scannerFilter = ScannerFilter.builder()
                .timeRange(Optional.of(DAY))
                .keywords(Optional.of("Java Developer"))
                .location(Optional.of("Germany"))
                .build();
        LinkedInScanner scanner = new LinkedInScanner(scannerFilter);
        List<JobAd> ads = scanner.scan();

        JobAdFilter filter = new JobAdFilter(scannerFilter);
        List<JobAd> filtered = filter.filter(ads);

        print(filtered);
        JobAdsExcelWriter excelWriter = new JobAdsExcelWriter();
        excelWriter.write(filtered);
    }

    public static void print(List<JobAd> ads) {
        for(JobAd ad : ads) {
            System.out.printf((PATTERN) + "%n", ad.getFirmaName(), ad.getTitle(), ad.getLocation(), ad.getUrl());
        }
    }
}
