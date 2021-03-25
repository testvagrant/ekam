package com.testvagrant.ekam.atoms;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.annotations.StaleHandler;
import com.testvagrant.ekam.reports.ReportLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;

public abstract class BasePage<T> implements WebActions<T> {

    @Inject
    WebDriver webDriver;

    @Inject
    WebDriverWait webDriverWait;

    T page;

    @Inject
    @Named("persona")
    private String persona;

    public BasePage() {
        init((T) this);
    }

    public T init(T page) {
        this.page = page;
        PageFactory.initElements(webDriver, page);
        return page;
    }

    @Override
    public void get(String url) {
        webDriver.get(url);
    }

    @Override
    @Screenshot
    public T click(WebElement element) {
        waitForElementToBeClickable(element).click();
        return page;
    }

    @Override
    public String url() {
        return webDriver.getCurrentUrl();
    }

    public void click(WebElement element, String filedToLog) {
        log(String.format("clicks on %s", filedToLog));
        click(element);
    }

    @Override
    @Screenshot @StaleHandler
    public T type(WebElement element, String textToType) {
        waitForElementToBePresent(element);
        click(element);
        element.clear();
        element.sendKeys(textToType);
        return page;
    }

    public void type(WebElement element, String textToType, String fieldNameToLog) {
        log(String.format("enters %s as %s", fieldNameToLog, textToType));
        type(element, textToType);
    }

    @Override @StaleHandler
    public String value(WebElement element) {
        waitForElementToBePresent(element);
        return element.getAttribute("value");
    }

    public void switchToFrame(String frameId) {
        webDriver.switchTo().frame(frameId);
    }

    public String title() {
        return webDriver.getTitle();
    }

    @Override
    public T refresh() {
        return init((T) this);
    }

    @StaleHandler
    public String text(WebElement webElement) {
        return waitForElementToBePresent(webElement).getText().trim();
    }

    public String text(WebElement webElement, String fieldToLog) {
        log(String.format("gets text of %s", fieldToLog));
        return text(webElement);
    }
    @StaleHandler
    public WebElement waitForElementToBePresent(WebElement webElement) {
        return webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
    }

    @StaleHandler
    public void waitForElementOnlyIfPresent(WebElement webElement, int timeout) {
       try {
           WebDriverWait webDriverWait = new WebDriverWait(webDriver, timeout);
           webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
       } catch (Exception e) {
           // skip exception
       }
    }

    @StaleHandler
    public WebElement waitForElementToBeClickable(WebElement webElement) {
        waitForElementToBePresent(webElement);
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    @StaleHandler
    public Boolean waitForFrameToLoad(String frameId) {
        webDriverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameId));
        return true;
    }

    @StaleHandler
    public Boolean waitForFrameToLoad(Integer index) {
        webDriverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
        return true;
    }

    public void waitForPageToLoad() {
        webDriverWait.until(webDriver1 -> ((JavascriptExecutor) webDriver1).executeScript("return document.readyState").equals("complete"));
    }

    @StaleHandler
    public WebElement findFromList(List<WebElement> elementsList, String attribute, String matcher) {
        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(elementsList));
        Optional<WebElement> item = elementsList.stream().filter(element -> element.getAttribute(attribute).toLowerCase().contains(matcher.toLowerCase()))
                .findFirst();
        return item.orElseThrow(RuntimeException::new);
    }

    @StaleHandler
    public WebElement findFromList(List<WebElement> elementsList, String matcher) {
        Optional<WebElement> item = elementsList.stream().filter(element -> {
            waitForElementsToBeDisplayed(elementsList);
            return  element.getText().toLowerCase().trim().contains(matcher.toLowerCase());
        }).findFirst();
        return item.orElseThrow(RuntimeException::new);
    }

    @StaleHandler
    public WebElement findFromList(By elementBy, String matcher) {
        List<WebElement> elementsList = waitForElementsToBePresent(elementBy);
        Optional<WebElement> item = elementsList.stream().filter(element -> element.getText().toLowerCase().trim().equalsIgnoreCase(matcher)).findFirst();
        return item.orElseThrow(RuntimeException::new);
    }

    @StaleHandler
    public void waitForElementToBeInvisible(WebElement webElement) {
        webDriverWait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    public void switchToDefaultContent() {
        webDriver.switchTo().defaultContent();
    }

    @Override
    public void log(String message) {
        ReportLogger.log(persona, message);
    }

    public void sleep() {
       this.sleep(1000);
    }

    public void sleep(int time) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void setAttribute(WebElement element, String attName, String attValue) {
        jsDriver().executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                element, attName, attValue);
    }

    @StaleHandler
    public List<WebElement> waitForElementsToBeDisplayed(List<WebElement> elements) {
       return webDriverWait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    @StaleHandler
    public List<WebElement> waitForElementsToBePresent(By elements) {
        return webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elements));
    }

    public void switchTab() {
        String currentWindowHandle = webDriver.getWindowHandle();
        Optional<String> tab = webDriver.getWindowHandles().stream()
                .filter(handle -> !handle.equals(currentWindowHandle))
                .findFirst();
        tab.ifPresent(s -> webDriver.switchTo().window(s));
    }

    private JavascriptExecutor jsDriver() {
        return ((JavascriptExecutor)webDriver);
    }

    @StaleHandler
    protected List<WebElement> getColumns(WebElement row) {
        return row.findElements(By.cssSelector("td"));
    }
}
