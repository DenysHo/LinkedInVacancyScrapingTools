package com.go.denys.selenium.automatization.linkedin.jobs.scaner.web.driver.scanner;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.ScannerFilter;
import lombok.Data;
import org.apache.http.client.utils.URIBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
@Data
public class LinkedInScanner extends WebDriverScanner {

    public static final String JOB_TITLE_XPATH = "//h2[@class='top-card-layout__title font-sans text-lg papabear:text-xl font-bold leading-open text-color-text mb-0 topcard__title']";
    public static final String URL = "https://www.linkedin.com/jobs/search";
    public static final String KEYWORDS_PARAMETER = "keywords";
    public static final String LOCATION_PARAMETER = "location";

    public static final String LINKEDIN_URL = "https://www.linkedin.com/";
    public static final String DISMISS_BUTTON = "//button[@class='modal__dismiss btn-tertiary h-[40px] w-[40px] p-0 rounded-full indent-0 contextual-sign-in-modal__modal-dismiss absolute right-0 m-[20px] cursor-pointer']";
    public static final String MODAL_DISMISS_BUTTON = "//button[contains(@class,'modal__dismiss') and @data-tracking-control-name='public_jobs_contextual-sign-in-modal_modal_dismiss']";
    public static final String BUTTON_PRIMARY_DATA = "button.artdeco-global-alert-action.artdeco-button--inverse.artdeco-button--2.artdeco-button--primary[data-control-name='ga-cookie.consent.accept.v4']";
    public static final String HEADER_JOB_COUNT = "//span[@class='results-context-header__job-count']";
    public static final String JOBS_SEARCH_RESULTS_LIST_CSS = "ul.jobs-search__results-list a[data-row][data-column], ul.jobs-search__results-list div[data-row][data-column]";
    public static final int OPEN_NEW_AD_TRY_COUNT = 20;
    public static final String TIME_RANGE_FILTER_BUTTON_CSS = "button[data-tracking-control-name='public_jobs_f_TPR']";
    public static final String SHOW_MORE_BUTTON_XPATH = "//button[@class='infinite-scroller__show-more-button infinite-scroller__show-more-button--visible']";
    public static final String SUBMIT_TIME_RANGE_FILTER_BUTTON_XPATH = "//button[@class='filter__submit-button' and @data-tracking-control-name='public_jobs_f_TPR']";
    public static final String SHOW_ALL_DESCRIPTION_BUTTON_XPATH = "//button[contains(@class, 'show-more-less-html__button')]";
    public static final String FIRMA_NAME_ELEMENT_XPATH = "//span[@class='topcard__flavor']/*[self::a or text()]";
    public static final String FIRMA_NAME_ANOTHER_ELEMENT_XPATH = "//span[@class='topcard__flavor']";
    public static final String DEFAULT_FIRMA_NAME = "not found!";
    public static final String LINK_ELEMENT_XPATH = "//a[@data-tracking-control-name='public_jobs_topcard-title']";
    public static final String URL_ELEMENT_ATTRIBUTE = "href";
    public static final String LOCATION_ELEMENT_XPATH = "//span[@class='topcard__flavor topcard__flavor--bullet']";
    public static final String DESCRIPTION_ELEMENT_XPATH = "//div[contains(@class, 'show-more-less-html__markup')]";

    private static final Logger logger = LoggerFactory.getLogger(LinkedInScanner.class);

    private ScannerFilter filter;

    public LinkedInScanner(ScannerFilter filter) {
        super();
        this.filter = filter;
    }

    public LinkedInScanner() {
        super();
        filter = new ScannerFilter();
    }

    public List<JobAd> scan() throws URISyntaxException {
        openJobAds();

        String jobAdCount = findElementByXpath(HEADER_JOB_COUNT).getText();
        logger.info("The number of Ads is written on the website = {}", jobAdCount);

        List<JobAd> jobAds = getJobList();
        logger.info("Count scanned Ads = {}", jobAds.size());

        return jobAds;
    }

    private void openJobAds() throws URISyntaxException {
        URI uri = formUri();

        try {
            get(uri.toString());
            String currentUrl = getDriver().getCurrentUrl();
            while (!currentUrl.contains(URL)) {
                get(uri.toString());
                currentUrl = getDriver().getCurrentUrl();
            }
            clickElementByXpathIfExists(DISMISS_BUTTON);
            clickElementByXpathIfExists(MODAL_DISMISS_BUTTON);
            clickElementIfExists(By.cssSelector(BUTTON_PRIMARY_DATA));
            Thread.sleep(2000);

            filter();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private URI formUri() throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(URL);
        filter.getKeywords().ifPresent(keywords -> uriBuilder.addParameter(KEYWORDS_PARAMETER, keywords));
        filter.getLocation().ifPresent(location -> uriBuilder.addParameter(LOCATION_PARAMETER, location));
        return uriBuilder.build();
    }

    public List<JobAd> getJobList() {
        List<JobAd> jobs = new ArrayList<>();
        try {
            int countProcessedElements = 0;
            List<WebElement> divElements;

            boolean moreElementsExist = true;
            int counter = OPEN_NEW_AD_TRY_COUNT;
            while (moreElementsExist || counter > 0) {
                try {
                    By cssSelector = By.cssSelector(JOBS_SEARCH_RESULTS_LIST_CSS);
                    divElements = getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(cssSelector));
                    divElements = divElements.stream().skip(countProcessedElements).toList();

                    if (divElements.isEmpty()) {
                        counter--;
                        openMoreJobAd();
                    }

                    if (divElements.isEmpty()) {
                        moreElementsExist = false;
                    } else {
                        counter = OPEN_NEW_AD_TRY_COUNT;
                        countProcessedElements += divElements.size();
                        logger.info("Count processed elements = {}", countProcessedElements);

                        for (int i = 0; i < divElements.size(); i++) {
                            WebElement div = divElements.get(i);
                            scroll(div);
                            click(div, 1000);

                            String dataRowId = div.getAttribute("data-row");

                            logger.info("Job ad row number = {}", dataRowId);

                            openAdContentIfNeeded(i, divElements, div);

                            JobAd jobAd = resolveJobAd(dataRowId);
                            jobs.add(jobAd);

                            if (filter.getLimit() > 0 && filter.getLimit() >= jobs.size()) {
                                moreElementsExist = false;
                                counter = 0;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.info("An error occurred: {}", e.getMessage(), e);
                    moreElementsExist = false;
                }
            }
        } finally {
            getDriver().quit();
        }
        return jobs;
    }

    private void openAdContentIfNeeded(int i, List<WebElement> divElements, WebElement div) throws InterruptedException {
        try {
            //WebElement jobTitleElement = findElementByXpath(JOB_TITLE_XPATH);
            Optional<WebElement> jobTitleElement = findElementOptionalByXpath(JOB_TITLE_XPATH);

            while (jobTitleElement.isEmpty() || jobTitleElement.get().getText().isEmpty()) {
                //open next
                int nextI = i == divElements.size() - 1 ? i - 1 : i + 1;
                WebElement nextDiv = divElements.get(nextI);
                scroll(nextDiv);
                click(nextDiv);
                scroll(div);
                click(div, 1000);
                jobTitleElement = findElementOptionalByXpath(JOB_TITLE_XPATH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openMoreJobAd() throws InterruptedException {
        Optional<WebElement> showMoreButton = findElementOptionalByXpath(SHOW_MORE_BUTTON_XPATH);
        if (showMoreButton.isPresent()) {
            scroll(showMoreButton.get());
            click(showMoreButton.get());
        } else {
            scroll(200);
            scroll(-200);
        }
    }

    private JobAd resolveJobAd(String id) throws InterruptedException {
        JobAd jobAd = new JobAd();

        jobAd.setId(id);

        WebElement jobTitleElement = findElementByXpath(JOB_TITLE_XPATH);
        jobAd.setTitle(jobTitleElement.getText());

        Optional<WebElement> firmaNameElement = findElementOptionalByXpath(FIRMA_NAME_ELEMENT_XPATH);
        if (firmaNameElement.isEmpty()) {
            firmaNameElement = findElementOptionalByXpath(FIRMA_NAME_ANOTHER_ELEMENT_XPATH);
        }
        jobAd.setFirmaName(firmaNameElement.isPresent() ? firmaNameElement.get().getText() : DEFAULT_FIRMA_NAME);


        WebElement linkElement = findElementByXpath(LINK_ELEMENT_XPATH);
        jobAd.setUrl(linkElement.getAttribute(URL_ELEMENT_ATTRIBUTE));
        WebElement locationElement = findElementByXpath(LOCATION_ELEMENT_XPATH);
        jobAd.setLocation(locationElement.getText());

        WebElement showMoreButton = findElementByXpath(SHOW_ALL_DESCRIPTION_BUTTON_XPATH);
        showMoreButton.click();
        Thread.sleep(1000);

        WebElement jobDescriptionElement = findElementByXpath(DESCRIPTION_ELEMENT_XPATH);
        jobAd.setDescription(jobDescriptionElement.getText());
        return jobAd;
    }

    private void filter() throws InterruptedException {
        if (filter.getTimeRange().isPresent()) {
            click(findElementByСssSelector(TIME_RANGE_FILTER_BUTTON_CSS));
            click(findElementById(filter.getTimeRange().get().getId()));
            click(findElementByXpath(SUBMIT_TIME_RANGE_FILTER_BUTTON_XPATH));
        }
    }
}
