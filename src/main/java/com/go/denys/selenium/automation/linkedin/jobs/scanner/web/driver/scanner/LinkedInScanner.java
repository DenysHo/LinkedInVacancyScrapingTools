package com.go.denys.selenium.automation.linkedin.jobs.scanner.web.driver.scanner;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.JobAd;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.LinkedInScannerFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class LinkedInScanner extends BaseScanner<LinkedInScannerFilter, JobAd> {


    private static final Logger logger = LoggerFactory.getLogger(LinkedInScanner.class);

    @Override
    protected void openJobAds() {

        try {
            String url = String.format("https://www.linkedin.com/jobs/search/" +
                    "?f_TPR=%s" +
                    "&keywords=%s" +
                    //todo
                    "&geoId=%s" +
                    "&origin=JOB_SEARCH_PAGE_JOB_FILTER" +
                    "&refresh=true" +
                    "&sortBy=R" +
                    "&position=1&pageNum=0",
                    filter.getTimeRange().getRange(),
                    filter.getKeywords().replaceAll(" ", "%20"),
                    //todo
                    filter.getLocation().equals("Ukraine") ? "102264497" : "101282230"
                    );

            get(url);

            if (getDriver().getPageSource().contains("HTTP ERROR")) {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.info("An error occurred: {}", e.getMessage(), e);
            openJobAds();
        }
    }

    @Override
    //todo userPassword
    protected void login() {
        get("https://www.linkedin.com/login");

        WebElement usernameField = getDriver().findElement(By.id("username"));
        usernameField.sendKeys("pashasom29@gmail.com");
        sleep(500);

        WebElement passwordField = getDriver().findElement(By.id("password"));
        passwordField.sendKeys("LinkedInPassword29Test!");
        sleep(500);

        WebElement signInButton = getDriver().findElement(By.xpath("//button[@aria-label='Sign in']"));
        signInButton.click();
        sleep(1500);
    }

    @Override
    protected String getAdCount() {
        Optional<WebElement> count = findElementOptionalByCssSelector("div.jobs-search-results-list__subtitle span");
        return count.isPresent() ? count.get().getText() : "0";
    }

    @Override
    protected List<JobAd> getJobList() {
        List<JobAd> jobs = new ArrayList<>();
        List<WebElement> adsElements;

        boolean moreElementsExist = true;
        int pageNum = 1;

        try {
            while (moreElementsExist) {
                adsElements = resolveCurrentPageAds(pageNum);


                if (adsElements.isEmpty()) {
                    moreElementsExist = false;
                } else {

                    for (WebElement div : adsElements) {
                        scroll(div, 150);
                        click(div, 500);

                        String dataRowId = div.getAttribute("data-job-id");

                        JobAd jobAd = resolveJobAd(dataRowId);
                        jobs.add(jobAd);
                        logger.info("Found job ad: {}", jobs.size());

                        if (filter.getLimit() > 0 && filter.getLimit() <= jobs.size()) {
                            moreElementsExist = false;
                            break;
                        }
                    }

                    if (moreElementsExist) {
                        Optional<WebElement> nextPage = findElementOptionalByXpath(String.format("//button[@aria-label='Page %s']", ++pageNum));
                        if (nextPage.isPresent()) {
                            nextPage.get().click();
                        } else {
                            moreElementsExist = false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info("An error occurred: {}", e.getMessage(), e);
        }
        return jobs;
    }

    private List<WebElement> resolveCurrentPageAds(int pageNum) {
        List<WebElement> divElements;
        //WebElement targetElement = getDriver().findElement(By.className("qUcJfenVGiLNIkncLqkdXjGkDEJUiENgvE"));
        WebElement targetElement = findElementByXpath("//div[contains(@class, 'scaffold-layout__list') and @tabindex='-1']/div");
        getJs().executeScript("arguments[0].focus();", targetElement);

        WebElement pageButton = findElementByXpath(String.format("//button[@aria-label='Page %s']", pageNum));
        scrollSmooth(pageButton, targetElement);

        By cssSelector =By.cssSelector("div.job-card-container");
        divElements = getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(cssSelector));
        return divElements;
    }

    private JobAd resolveJobAd(String id) {
        JobAd jobAd = new JobAd();

        jobAd.setId(id);

        try {
            Optional<WebElement> jobTitle = findElementOptionalByCssSelector("h1.t-24.t-bold.inline");
            jobAd.setTitle(jobTitle.isPresent() ? jobTitle.get().getText() : "Job Title Not Found");

            Optional<WebElement> firmaName = findElementOptionalByCssSelector(".job-details-jobs-unified-top-card__company-name");
            jobAd.setFirmaName(firmaName.isPresent() ? firmaName.get().getText() : "Firma Name Not Found");

            Optional<WebElement> link = findElementOptionalByCssSelector(".t-24.job-details-jobs-unified-top-card__job-title a");
            jobAd.setUrl(link.isPresent() ? link.get().getAttribute("href") : "Url Not Found");

            Optional<WebElement> location = findElementOptionalByCssSelector(".job-details-jobs-unified-top-card__primary-description-container .tvm__text.tvm__text--low-emphasis");
            jobAd.setLocation(location.isPresent() ? location.get().getText().trim() : "Location Not Found");

            Optional<WebElement> jobDescription = findElementOptionalById("job-details");
            jobAd.setDescription(jobDescription.isPresent() ? jobDescription.get().getText() : "Job Description Not Found");
        } catch (Exception e) {
            logger.info("An error occurred: {}", e.getMessage(), e);
        }

        return jobAd;
    }
}
