package com.testvagrant.ekam.atoms;

import com.testvagrant.ekam.commons.annotations.Screenshot;
import org.openqa.selenium.WebElement;

/**
 * A Wrapper for WebDriver and WebElement actions.
 * <p>
 * Useful to add listeners around to generate custom reports
 */
public interface WebActions<T> {

    /**
     * Initialize page elements
     *
     * @param page
     * @return page
     */

    @Screenshot
    default T init(T page) {
        throw new UnsupportedOperationException();
    }

    /**
     * Launches url on browser. {@link org.openqa.selenium.WebDriver#get(String)}
     *
     * @param url
     */
    default void get(String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@link WebElement#click}
     */
    default T click(WebElement element) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@link WebElement#sendKeys(CharSequence...)}
     */
    default T type(WebElement element, String textToType) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get current page title
     *
     * @return pageTitle
     */
    default String title() {
        return "";
    }

    /**
     * Refresh page for {@link org.openqa.selenium.StaleElementReferenceException}
     *
     * @return T page
     */
    default T refresh() {
        throw new UnsupportedOperationException();
    }

    /**
     * Finds Element text
     *
     * @param element
     * @return element text
     */
    default String text(WebElement element) {
        return "";
    }


    /**
     * Get attribute value for input fields
     */
    default String value(WebElement element) {
        return element.getAttribute("value");
    }

    /**
     * Explicit wait for element. Defaults to {@link org.openqa.selenium.support.ui.ExpectedConditions#visibilityOf(WebElement)}
     *
     * @param element
     * @return
     */
    default WebElement waitForElementToBePresent(WebElement element) {
        return null;
    }

    default void switchToFrame(String frameId) {
        throw new UnsupportedOperationException();
    }

    default Boolean waitForFrameToLoad(String frameId) {
        return false;
    }

    default Boolean waitForFrameToLoad(Integer index) {
        return false;
    }

    default void switchToDefaultContent() {
        throw new UnsupportedOperationException();
    }

    default void log(String message) {
        throw new UnsupportedOperationException();
    }

    default String url() {
        throw new UnsupportedOperationException();
    }
}

