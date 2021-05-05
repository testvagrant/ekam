package com.testvagrant.ekam.web.pages;

import com.testvagrant.ekam.atoms.web.WebPage;
import com.testvagrant.ekam.commons.PageInitiator;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

public class GooglePage extends WebPage {

  By searchTextBox = queryByName("q");

  @Step(keyword = "When", description = "User Searches for key")
  @Screenshot(name = "Search")
  public void search(String searchKey) {
    textbox(searchTextBox).setText(searchKey);
  }


  @Step(description = "User is on home page")
  @Screenshot()
  public void onHomePage() {

  }

  @Step(keyword = "Then", description = "User find relevent search")
  @Screenshot(name = "Search page")
  public void onSearchPage(boolean condition) {
    Assert.assertTrue(condition);
  }

  @Step(keyword = "And", description = "User clicks on topSuggested result")
  @Screenshot
  public void skippedStep() {
  }
}
