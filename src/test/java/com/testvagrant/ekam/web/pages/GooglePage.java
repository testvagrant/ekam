package com.testvagrant.ekam.web.pages;

import com.testvagrant.ekam.atoms.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.testvagrant.ekam.atoms.web.Textbox.Textbox;

public class GooglePage extends BasePage {

  By searchTextBox = queryByName("q");

  @Step("Search")
  public void search(String searchKey) {
    Textbox(searchTextBox).setText(searchKey);
  }
}
