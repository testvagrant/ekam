package com.testvagrant.ekam.web;

import com.testvagrant.ekam.commons.PageInitiator;
import com.testvagrant.ekam.testbase.EkamWebTest;
import com.testvagrant.ekam.web.pages.GooglePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static com.testvagrant.ekam.commons.PageInitiator.WebPage;

public class WebTests extends EkamWebTest {

  @Test(groups = "web")
  @Story("Abc")
  @Feature("Hello")
  @Epic("APC")
  public void searchTest() {
    GooglePage googlePage = WebPage(GooglePage.class);
    googlePage.onHomePage();
    googlePage.search("Calling from Ekam");
    googlePage.onSearchPage(false);
    googlePage.skippedStep();
    PageInitiator.getInstance().captureScreenshot();
  }

  @Test(groups = "web")
  @Story("Abc")
  @Feature("Hello")
  @Epic("APC")
  public void searchTest1() {
    GooglePage googlePage = WebPage(GooglePage.class);
    googlePage.onHomePage();
    googlePage.search("Calling from Ekam");
    googlePage.onSearchPage(true);
    googlePage.skippedStep();
    PageInitiator.getInstance().captureScreenshot();
  }
}
