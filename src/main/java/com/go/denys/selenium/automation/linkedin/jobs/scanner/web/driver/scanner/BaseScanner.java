package com.go.denys.selenium.automation.linkedin.jobs.scanner.web.driver.scanner;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.JobAd;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.ScannerFilter;
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
            login();
            openJobAds();

            String jobAdCount = getAdCount();
            //todo write this to ScanResult<A>
            logger.info("The number of Ads is written on the website = {}", jobAdCount);

            jobAds.addAll(getJobList());
            logger.info("Count scanned Ads = {}", jobAds.size());
        }catch (Exception e) {
            logger.info("An error occurred: {}", e.getMessage(), e);
            openJobAds();
            jobAds = new ArrayList<>();
        } finally {
            getDriver().quit();
        }
        return jobAds;
    }

    protected abstract List<A> getJobList();

    protected abstract void openJobAds() throws URISyntaxException;

    protected abstract String getAdCount();

    protected abstract void login();

}
