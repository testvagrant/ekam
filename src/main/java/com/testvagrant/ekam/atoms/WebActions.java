package com.testvagrant.ekam.atoms;

import com.testvagrant.ekam.commons.annotations.Screenshot;
import org.openqa.selenium.WebElement;

/**
 * A Wrapper for WebDriver and WebElement actions.
 *
 * Useful to add listeners around to generate custom reports
 */
public interface WebActions<T>  {

    /**
     * Initialize page elements
     * @param page
     * @return page
     */

    @Screenshot
    T init(T page);

    /**
     * Launches url on browser. {@link org.openqa.selenium.WebDriver#get(String)}
     * @param url
     */
    void get(String url);

    /**
     * {@link WebElement#click}
     */
    T click(WebElement element);

    /**
     * {@link WebElement#sendKeys(CharSequence...)}
     */
    T type(WebElement element, String textToType);

    /**
     * Get current page title
     * @return pageTitle
     */
    default String title() { return ""; }

    /**
     * Refresh page for {@link org.openqa.selenium.StaleElementReferenceException}
     * @return T page
     */
    T refresh();

    /**
     * Finds Element text
     * @param element
     * @return element text
     */
    default String text(WebElement element) { return ""; }


    /**
     * Get attribute value for input fields
     */
    default String value(WebElement element) { return element.getAttribute("value");}

    /**
     * Explicit wait for element. Defaults to {@link org.openqa.selenium.support.ui.ExpectedConditions#visibilityOf(WebElement)}
     * @param element
     * @return
     */
    default WebElement waitForElementToBePresent(WebElement element) { return null; }

    void switchToFrame(String frameId);

    Boolean waitForFrameToLoad(String frameId);

    Boolean waitForFrameToLoad(Integer index);

    void switchToDefaultContent();

    void log(String message);

    String url();
}

