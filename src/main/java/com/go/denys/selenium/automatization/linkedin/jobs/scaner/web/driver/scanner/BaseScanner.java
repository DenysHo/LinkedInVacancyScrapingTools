package com.go.denys.selenium.automatization.linkedin.jobs.scaner.web.driver.scanner;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.ScannerFilter;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public abstract class BaseScanner<F extends ScannerFilter, A extends JobAd> extends WebDriverScanner {

    private static final Logger logger = LoggerFactory.getLogger(BaseScanner.class);

    protected F filter;

    public BaseScanner() {
        super();
    }

    public List<A> scan() throws URISyntaxException {
        List<A> jobAds = new ArrayList<>();
        try {
            openJobAds();

            String jobAdCount = getAdCount();
            logger.info("The number of Ads is written on the website = {}", jobAdCount);

            jobAds.addAll(getJobList());
            logger.info("Count scanned Ads = {}", jobAds.size());
        } finally {
            getDriver().quit();
        }
        return jobAds;
    }

    protected abstract List<A> getJobList();

    protected abstract void openJobAds() throws URISyntaxException;

    protected abstract String getAdCount();

}
