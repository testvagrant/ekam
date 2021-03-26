package com.testvagrant.ekam.atoms.base;

import com.google.inject.Inject;
import com.testvagrant.ekam.atoms.MobileActions;
import com.testvagrant.optimuscloud.entities.MobileDriverDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.Runtime.getRuntime;

public abstract class BaseActivity<T> implements MobileActions<BaseActivity> {

    protected AppiumDriver driver;

    @Inject
    WebDriverWait wait;

    @Inject
    MobileDriverDetails mobileDriverDetails;

    T page;

    private String option = "//android.widget.TextView[@text=\"%s\"]";

    public BaseActivity init(BaseActivity page) {
        this.driver = (AppiumDriver) mobileDriverDetails.getMobileDriver();
        FieldDecorator fieldDecorator = new AppiumFieldDecorator(driver, Duration.ofSeconds(30));
        PageFactory.initElements(fieldDecorator, page);
        return  page;
    }

    public BaseActivity click(WebElement webElement) {
        waitForElementToBeClickable(webElement);
        webElement.click();
        return this;
    }

    public BaseActivity click(WebElement webElement, int timeout) {
        waitForElementToBeClickable(webElement, timeout).click();
        return this;
    }


    public BaseActivity clickIfPresent(WebElement webElement) {
        try{
            return click(webElement, 2);
        } catch (Exception e) {
            return this;
        }
    }

    public String text(WebElement element) {
       return waitForElementToBeVisible(element).getText();
    }

    public BaseActivity type(WebElement webElement, String value) {
        waitForElementToBeClickable(webElement).sendKeys(value);
        return this;
    }


    private static ExpectedCondition<WebElement> elementToBeChecked(
            final WebElement element) {
        return new ExpectedCondition<WebElement>() {

            public ExpectedCondition<WebElement> visibilityOfElement =
                    ExpectedConditions.visibilityOf(element);

            @Override
            public WebElement apply(WebDriver driver) {
                WebElement element = visibilityOfElement.apply(driver);
                try {
                    if (element != null && element.getAttribute("checked").equals("true")) {
                        return element;
                    } else {
                        return null;
                    }
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "element to be checked : " + element;
            }
        };
    }

    public boolean allowPermissionPopup() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        try {
            By allowXpath = By.xpath("//*[@text='Allow' or @name = 'Allow']");
            WebElement acceptElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(allowXpath));
            acceptElement.click();
            acceptElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(allowXpath));
            acceptElement.click();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void waitForElementToBeVisible(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public boolean waitForElementToBeVisible(By by, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitForElementToBeVisible(WebElement webElement, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        try {
            wait.until(ExpectedConditions.visibilityOf(webElement));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void click_last(List<WebElement> element) {
        waitForElementToBeClickable(getLast(element));
        getLast(element).click();
    }

    public WebElement getLast(List<WebElement> element) {
        return element.get(element.size() - 1);
    }

    public WebElement waitForElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForElementToBeClickable(WebElement element, int timeout) {
        return newWait(timeout).until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForTextToBePresentInElement(WebElement element, String text) {
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public void waitForElementToBeSelected(WebElement element) {
        wait.until(ExpectedConditions.elementSelectionStateToBe(element, true));
    }

    public void waitForElementToBeRefreshed(WebElement element) {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
    }

    public void waitForElementToBeRefreshed(By by) {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(by)));
    }

    public WebElement waitForElementToBeClickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public WebElement waitForPresenceOfElement(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void waitForPresenceOfAllElements(By by) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public boolean waitForTextToBePresentInElement(By by, String text) {
        try {
            if (wait.until(ExpectedConditions.textToBePresentInElementLocated(by, text)))
                return true;
        } catch (TimeoutException e) {
            return false;
        }
        return false;
    }

    public boolean waitForTextToBeNonEmpty(final By by) {
        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(by).getText().length() != 0;
            }
        });
        return false;
    }

    public void waitForInvisibilityOfElementByText(By by, String text) {
        wait.until(ExpectedConditions.invisibilityOfElementWithText(by, text));
    }

    public void waitForElementToBeInVisible(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForElementToBeInVisible(WebElement element, int timeout) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeout);
        webDriverWait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForElementToBeInvisible(By by) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void waitForElementToBeChecked(WebElement element) {
        wait.until(elementToBeChecked(element));
    }

    public void sendKeys(WebElement elem, String text) {
        waitForElementToBeClickable(elem);
        elem.click();
        if (text != null) {
            if (!elem.getText().isEmpty()) {
                elem.clear();
            }
            elem.sendKeys(text);
        } else {
            Assert.assertNotNull(elem.getText());
        }
        driver.getKeyboard();
        hideKeyboard();
    }

    public void hideKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (WebDriverException e) {
        }
    }

//    public void scrollTo(String text) {
//        try {
//            riderDriver.scrollTo(text);
//        } catch (WebDriverException e) {
//
//        }
//    }

    public void scrollTo(String text) {
        scrollDownTo(text);
    }

    public void scrollDownTo(String text) {
        scrollDownTo(By.xpath("//*[@text=\'" + text + "\' or @name=\'" + text + "\']"));
    }

    public void tapOn(String text) {
        driver.findElement(By.xpath("//*[@text=\'" + text + "\']")).click();
    }

    public void scrollDownToContainsText(String text) {
        scrollDownTo(By.xpath("//*[contains(@text,\'" + text + "\') or contains(@name,\'" + text + "\')]"));
    }

    public void scrollDownTo(String attribute, String text) {

        switch(attribute) {
            case "content-desc":
                scrollDownWithoutFailTo(By.xpath("//*[@content-desc=\'" + text + "\']"));
                break;
        }
    }

    public void checkIsDisplayed(String text) throws Exception {
        driver.findElement(By.xpath("//*[@text=\'" + text + "\']")).isDisplayed();
    }

    public void scrollDownTo(By byOfElementToBeFound) {
        hideKeyboard();
        int i = 0;
        while (i < 12) {
            if (driver.findElements(byOfElementToBeFound).size() > 0)
                return;

            scrollDown();

            i++;
        }
    }

    public void scrollDownWithoutFailTo(By byOfElementToBeFound) {
        hideKeyboard();
        int i = 0;
        while (i < 4) {
            if (driver.findElements(byOfElementToBeFound).size() > 0)
                return;

            scrollDown();

            i++;
        }
    }

    public BaseActivity scrollDownTo(WebElement element) {
        hideKeyboard();
        int i = 0;
        while (i < 12) {
            try {
                scrollDown();
                if (element.isDisplayed()) return this;
            } catch (Exception e) {

            }
            i++;
        }
        return this;
    }

    public void scrollDownWithoutFailTo(WebElement element) {
        hideKeyboard();
        int i = 0;
        while (i < 12) {
            try {
                if (element.isDisplayed())
                    return;
            } catch (Exception e) {

            }

            scrollDown();

            i++;
        }
    }

    public void scrollDownWithoutFailStartInvesting(WebElement element) {
        hideKeyboard();
        int i = 0;
        while (i < 2) {
            try {
                if (element.isDisplayed())
                    return;
            } catch (Exception e) {

            }

            scrollDown();

            i++;
        }
    }

    public void scrollDownTo(List<WebElement> initSize) {
        hideKeyboard();
        int i = 0;
        int size = initSize.size();
        while (i < 12) {
            if (size < initSize.size()) {
                break;
            }
            scrollDown();
            i++;
        }
    }

    public void scrollDown() {
        int height = driver.manage().window().getSize().getHeight();

        PointOption pointOption = new PointOption();
        pointOption.withCoordinates(5, height * 2 / 3);

        PointOption moveToPointOption = new PointOption();
        moveToPointOption.withCoordinates(5, height / 3);
        WaitOptions waitOptions = new WaitOptions();
        waitOptions.withDuration(Duration.ofMillis(100));
        new TouchAction(driver).press(pointOption)
                .waitAction(waitOptions)
                .moveTo(moveToPointOption)
                .release().perform();

    }

    public void scrollDown(int times) {
        IntStream.range(0, times)
                .forEach(time -> scrollDown());
    }

    public void scrollDownUntilElementIsEnabled(WebElement webElement, int initialScroll, int followupScroll) {
        boolean elementNotVisible = false;
        while (!elementNotVisible) {
            try {
                scrollDown(initialScroll);
                waitForElementToBeVisible(webElement, 1);
                elementNotVisible = true;
            } catch (Exception e) {
                scrollDown(followupScroll);
            }
        }
    }

    public void scrollUp() {

        int height = driver.manage().window().getSize().getHeight();

//        new TouchAction(driver).press(5, height / 3)
//                .waitAction(Duration.ofMillis(1000))
//                .moveTo(5, height * 2 / 3)
//                .release().perform();
    }

    public void swipeLeftToRight() {
        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();
//        new TouchAction(driver).press(width / 3, height / 2)
//                .waitAction(Duration.ofMillis(1000))
//                .moveTo(width * 2 / 3, height / 2)
//                .release().perform();
    }

    public void swipeRightToLeft() {
        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();
//        new TouchAction(driver).press(width * 9 / 10, height / 2)
//                .waitAction(Duration.ofMillis(1000))
//                .moveTo(width / 10, height / 2)
//                .release().perform();
    }

    public void scrollUpTo(String text) {
        scrollUpTo(By.xpath("//*[@text=\"" + text + "\"]"));
    }

    public void scrollUpTo(By by) {
        hideKeyboard();
        int i = 0;
        while (i < 5) {
            if (driver.findElements(by).size() > 0)
                return;

            scrollUp();

            i++;
        }
    }

    public void clickOnListUsingIndex(By by, int index) {
        waitForPresenceOfAllElements(by);
        List<WebElement> locationNames = driver.findElements(by);
        locationNames.get(index).click();
    }

    public void scrollDownToByAndTap(By by) {
        scrollDownTo(by);
        driver.findElement(by).click();
    }


    public void swipeRightToLeftToFindElementAndClick(By byOfElementToSwipeOn, By byOfElementToBeFound) {

        int height = driver.findElement(byOfElementToSwipeOn).getLocation().getY() + 50;
        int width = driver.manage().window().getSize().getWidth();

        System.out.println("Screen width ::" + width);


        int count = 0;
        while (count < 20) {
            if (driver.findElements(byOfElementToBeFound).size() > 0) {
                driver.findElement(byOfElementToBeFound).click();
                return;
            }
//            new TouchAction(driver).press(width * 6 / 7, height)
//                    .waitAction(Duration.ofMillis(1000))
//                    .moveTo(width / 7, height)
//                    .release().perform();
            count++;
        }
    }

    public void swipeRtoLOnElementUsingCount(By by, int count) {

        Point p = driver.findElement(by).getLocation();
        System.out.println(by);
        int x_int = ((Integer) p.getX());
        int y_int = ((Integer) p.getY());


        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();
        System.out.println("x_int :: " + x_int);
        System.out.println("Screen width ::" + width);


        boolean flag = true;
        int _count = 0;
        while (flag) {
            try {
                if (_count == count) {
                    flag = false;
                } else {
//                    new TouchAction(driver).press(width - x_int, y_int)
//                            .waitAction(Duration.ofMillis(1000))
//                            .moveTo(x_int, y_int)
//                            .release().perform();

                    _count++;
                    System.out.println("Swipe Count :: " + _count);
                }
            } catch (Exception e) {
//                new TouchAction(driver).press(width - 100, y_int + 100)
//                        .waitAction(Duration.ofMillis(1000))
//                        .moveTo(x_int + 100, y_int + 100)
//                        .release().perform();

                _count++;
                System.out.println("Inside catch block");

            }
        }
    }


    public void clickBy(By by) {
        waitForElementToBeClickable(by);
        driver.findElement(by).click();
    }

    protected void swipeFromTo(WebElement startElement, WebElement stopElement) {
//        new TouchAction(driver).press(startElement.getLocation().getX(), startElement.getLocation().getY())
//                .waitAction(Duration.ofMillis(1000))
//                .moveTo(stopElement.getLocation().getX(), stopElement.getLocation().getY())
//                .release().perform();

    }

    public void swipeFromLeftToRight(WebElement webElement) {
        waitForElementToBeClickable(webElement);
        int xAxisStartPoint = webElement.getLocation().getX();
        int xAxisEndPoint = xAxisStartPoint + webElement.getSize().getWidth();
        int yAxis = webElement.getLocation().getY();
        TouchAction act = new TouchAction(driver);
        System.out.print(xAxisStartPoint + " " + yAxis);
//        act.longPress(xAxisStartPoint, yAxis).moveTo(xAxisEndPoint - 1, yAxis).release().perform();
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    protected boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean isElementPresent(WebElement element) {
        try {
            return waitForElementToBeVisible(element).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean clickAny(By... bys) {
        for (By by : bys)
            if (!driver.findElements(by).isEmpty()) {
                driver.findElement(by).click();
                waitForElementToBeInvisible(by);
                return true;
            }
        return false;
    }

    protected void clickTwice(WebElement element) {
        element.click();
        element.click();
    }

    protected void clickLastElementFromList(List<WebElement> list) {
        list.get(0).click();
        AtomicBoolean isNotClicked = new AtomicBoolean(true);
        while (isNotClicked.get()) {
            AtomicInteger initSize = new AtomicInteger(list.size());
            initSize.set(list.size());
            list.forEach(element -> {
                try {
                    waitForElementToBeVisible(element);
                    element.click();
                    waitForElementToBeVisible(element);
                    if (initSize.get() < list.size() || list.size() == 0)
                        isNotClicked.set(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    isNotClicked.set(false);
                }

            });
        }
    }

    public void executeCommand(String command) throws IOException, InterruptedException {
        Process process = getRuntime().exec(command);
        process.waitFor();
        System.out.println(process.exitValue());
    }

    protected <T> T getElementFile(Class<T> reference) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String name  = reference.getName();
        System.out.println(name);
        T obj = (T) Class.forName(name).newInstance();
        return obj;
    }

    public void switchToWebView() {
        Set<String> contextHandles = driver.getContextHandles();
        contextHandles.stream().forEach(context -> {
            driver.context(context);
        });
    }

    public void scrollToBottomInWeb() {
        jsDriver().executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public JavascriptExecutor jsDriver() {
        return ((JavascriptExecutor)driver);
    }

    private WebDriverWait newWait(int timeout) {
        return new WebDriverWait(driver, timeout);
    }

    public void openDeeplink(String link) {
        driver.get(link);
    }

    protected String getReferenceImageB64(String imageName) throws URISyntaxException, IOException {
        URL refImgUrl = getClass().getClassLoader().getResource("images/"+imageName);
        File refImgFile = Paths.get(refImgUrl.toURI()).toFile();
        return Base64.getEncoder().encodeToString(Files.readAllBytes(refImgFile.toPath()));
    }

    protected void tap(WebElement webElement) {
        touchAction()
                .tap(getPointOption(webElement))
                .perform();
    }

    private PointOption getPointOption(WebElement webElement) {
        return new PointOption().withCoordinates(webElement.getLocation());
    }

    protected TouchAction touchAction() {
        return new TouchAction(driver);
    }

    public void restartApp() {
        String bundleId = "";
        if(mobileDriverDetails.getDesiredCapabilities().getPlatform().is(Platform.ANDROID)) {
            bundleId = mobileDriverDetails.getDesiredCapabilities().getCapability("appPackage").toString();
        } else {
            bundleId = mobileDriverDetails.getDesiredCapabilities().getCapability("bundleId").toString();
        }
        driver.terminateApp(bundleId);
        driver.activateApp(bundleId);
    }


    protected void chooseOption(String optionValue) {
        By optionBy = By.xpath(String.format(option, optionValue));
        waitForElementToBeClickable(optionBy).click();
    }

    protected void clickAndChooseOption(MobileElement option, String optionValue) {
        click(option);
        chooseOption(optionValue);
    }
}
