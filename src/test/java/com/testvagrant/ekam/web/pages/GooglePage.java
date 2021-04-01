package com.testvagrant.ekam.web.pages;

import com.testvagrant.ekam.atoms.web.BasePage;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class GooglePage extends BasePage {

  By searchTextBox = queryByName("q");

  @Step("Search")
//  @Screenshot
  public void search(String searchKey) {
    TextBox(searchTextBox).setText(searchKey);
  }
}
