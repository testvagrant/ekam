package com.testvagrant.ekam.web;

import com.testvagrant.ekam.testbase.EkamWebTest;
import com.testvagrant.ekam.web.pages.GooglePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static com.testvagrant.ekam.commons.PageInitiator.Page;

public class WebTests extends EkamWebTest {

  @Test(groups = "web")
  @Story("Abc")
  @Feature("Hello")
  @Epic("APC")
  public void searchTest() {
    GooglePage googlePage = Page().getInstance(GooglePage.class);
    googlePage.search("Calling from Ekam");
  }

  @Test(groups = "web")
  @Story("Abc")
  @Feature("Hello")
  @Epic("APC")
  public void searchTest1() {
    GooglePage googlePage = Page().getInstance(GooglePage.class);
    googlePage.search("Calling from Ekam");
  }

  @Test(groups = "web")
  @Story("Abc")
  @Feature("Hello")
  @Epic("APC")
  public void searchTest2() {
    GooglePage googlePage = Page().getInstance(GooglePage.class);
    googlePage.search("Calling from Ekam");
  }

  @Test(groups = "web")
  @Story("Abc")
  @Feature("Hello")
  @Epic("APC")
  public void searchTest3() {
    GooglePage googlePage = Page().getInstance(GooglePage.class);
    googlePage.search("Calling from Ekam");
  }

  @Test(groups = "web")
  @Story("Abc")
  @Feature("Hello")
  @Epic("APC")
  public void searchTest4() {
    GooglePage googlePage = Page().getInstance(GooglePage.class);
    googlePage.search("Calling from Ekam");
  }
}
