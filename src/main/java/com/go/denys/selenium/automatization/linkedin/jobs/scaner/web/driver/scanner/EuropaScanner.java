package com.go.denys.selenium.automatization.linkedin.jobs.scaner.web.driver.scanner;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.EuropaJobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.EuropaScannerFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EuropaScanner extends BaseScanner<EuropaScannerFilter, EuropaJobAd> {

    private static final Logger logger = LoggerFactory.getLogger(EuropaScanner.class);
    public static final String NEXT_BUTTON_ID = "jv-navigation-next-button";
    public static final String JOB_AD_XPATH = "(//a[contains(@class, 'jv-result-summary-title ecl-link ecl-link--standalone ng-star-inserted')])[1]";
    public static final String JOB_ID_ID = "jv-handle";
    public static final String URI = "https://europa.eu/eures/portal/jv-se/search" +
            "?page=1&resultsPerPage=10" +
            "&orderBy=BEST_MATCH" +
            "&locationCodes=%s" +
            "&keywordsEverywhere=%s" +
            "&lang=en";

    @Override
    protected List<EuropaJobAd> getJobList() {
        List<EuropaJobAd> jobs = new ArrayList<>();

        WebElement firstAd = findElementByXpath("(//a[contains(@class, 'jv-result-summary-title ecl-link ecl-link--standalone ng-star-inserted')])[1]");
        scrollIntoView(firstAd, 1000);
        click(firstAd, 2000);

        int count = 1;

        while (true) {
            try {
                String jobId;
                String url;
                do {
                    jobId = findElementById(JOB_ID_ID).getText().replace("Job vacancy handle :", "").trim();
                    url = getDriver().getCurrentUrl();
                    sleep(500);
                } while (!url.contains(jobId));

                EuropaJobAd jobAd = resolveJobAd(jobId, url);
                jobs.add(jobAd);

                logger.info(String.format("Found job ad: %s", count++));

                WebElement nextButton = findElementById(NEXT_BUTTON_ID);
                String buttonClasses = nextButton.getAttribute("class");
                if (buttonClasses.contains("p-disabled") || filter.getLimit() != 0 && filter.getLimit() <= count) {
                    break;
                }
                boolean click = false;
                while (!click) {
                    try {
                        scrollIntoView(nextButton, 500);
                        click(nextButton, 1000);
                        click = true;
                    } catch (Exception e) {
                        //do nothing
                    }
                }
            } catch (Exception e) {
                logger.info("An error occurred: {}", e.getMessage(), e);
            }
        }
        return jobs;
    }

    private EuropaJobAd resolveJobAd(String id, String url) throws InterruptedException {
        EuropaJobAd jobAd = new EuropaJobAd();

        jobAd.setId(id);
        jobAd.setUrl(url);
        jobAd.setTitle(findElementById("jv-title").getText());

        Optional<WebElement> language = findElementOptionalByXpath("//span[@id='jv-working-language-codes-result']");
        language.ifPresent(element -> jobAd.setLanguages(element.getText()));


        Optional<WebElement> descriptionElement = findOptionElementById("jv-details-job-description");
        if (descriptionElement.isPresent()) {
            scrollIntoView(descriptionElement.get(), 500);
            jobAd.setDescription(descriptionElement.get().getText());
        }

        Optional<WebElement> firmaName = findOptionElementByCssSelector("div.ecl-u-mr-auto h2.ecl-u-type-heading-2");
        firmaName.ifPresent(element -> jobAd.setFirmaName(element.getText()));

        if (findOptionElementById("jv-details-job-location").isPresent()) {
            try {
                scrollIntoView(findElementById("jv-details-job-location"), 500);
                jobAd.setLocation(findElementById("jv-details-job-location").getText());
            } catch (Exception e) {
                //logger.info("An error occurred: {}", e.getMessage(), e);
            }
        }

        return jobAd;
    }

    @Override
    protected void openJobAds() {
        URI uri = java.net.URI.create(String.format(URI,
                filter.getLocations().get(0).getCode(),
                filter.getKeywords().replaceAll(" ", "%20")));

        try {
            get(uri.toString());
            sleep(2000);
        } catch (Exception e) {
            openJobAds();
        }
    }

    @Override
    protected String getAdCount() {
        String text = findElementByXpath("//h2[contains(@class, 'ecl-u-type-heading-5') and contains(@class, 'ecl-u-mt-none')]").getText();

        Pattern pattern = Pattern.compile("(\\d+[.,]?\\d*)\\s+vacancies$");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return text;
    }
}
