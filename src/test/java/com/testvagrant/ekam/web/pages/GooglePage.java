package com.testvagrant.ekam.web.pages;

import com.testvagrant.ekam.atoms.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class GooglePage extends BasePage {

  By searchTextBox = queryByName("q");

  @Step("Search")
  public void search(String searchKey) {
    TextBox(searchTextBox).setText(searchKey);
  }
}
