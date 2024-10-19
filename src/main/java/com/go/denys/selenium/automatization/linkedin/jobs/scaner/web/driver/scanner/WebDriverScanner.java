package com.go.denys.selenium.automatization.linkedin.jobs.scaner.web.driver.scanner;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Data
public class WebDriverScanner {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String SCROLL_SCRIPT = "window.scrollBy(0, %s);";

    public WebDriverScanner() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-search-engine-choice-screen");

        setDriver(new ChromeDriver(options));
        setWait( new WebDriverWait(getDriver(), Duration.ofSeconds(10)));
    }

    protected void get(String url) throws InterruptedException {
        driver.get(url);
        Thread.sleep(2000);
    }

    protected JavascriptExecutor getJs() {
        return (JavascriptExecutor) getDriver();
    }

    protected List<WebElement> findElementsByXpath(String xpath) {
        return driver.findElements(By.xpath(xpath));
    }

    protected List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    protected Optional<WebElement> findElementOptionalByXpath(String xpath) {
        return driver.findElements(By.xpath(xpath)).stream().findFirst();
    }

    protected WebElement findElementByCssSelector(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector));
    }

    protected WebElement findElementById(String id) {
        return driver.findElement(By.id(id));
    }

    protected WebElement findElementByXpath(String xpathExpression) {
        return driver.findElement(By.xpath(xpathExpression));
    }

    protected void clickElementIfExists(By by) throws InterruptedException {
        List<WebElement> elements = findElements(by);
        if (!elements.isEmpty()) {
            click(elements.get(0));
        }
    }

    protected void clickElementByXpathIfExists(String xpath) throws InterruptedException {
        List<WebElement> elements = findElementsByXpath(xpath);
        if (!elements.isEmpty()) {
            click(elements.get(0));
        }
    }

    protected void click(WebElement element, long millis) throws InterruptedException {
        element.click();
        Thread.sleep(millis);
    }

    protected void click(WebElement element) throws InterruptedException {
        click(element, 500);
    }

    protected void scroll(WebElement element, long millis) throws InterruptedException {
        getJs().executeScript("arguments[0].scrollIntoView(false);", element);
        Thread.sleep(millis);
    }

    protected void scroll(WebElement element) throws InterruptedException {
        scroll(element, 500);
    }

    protected void scroll(int px, long millis) throws InterruptedException {
        getJs().executeScript(String.format(SCROLL_SCRIPT, px));
        Thread.sleep(millis);
    }

    protected void scroll(int px) throws InterruptedException {
        scroll(px, 500);
    }
}
