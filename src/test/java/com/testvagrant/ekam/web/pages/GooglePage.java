package com.testvagrant.ekam.web.pages;

import com.testvagrant.ekam.atoms.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GooglePage extends BasePage {

    @FindBy(name = "q")
    private WebElement searchEle;

    @Step("Search")
    public void search(String searchKey) {
        type(searchEle, searchKey);
    }

}
