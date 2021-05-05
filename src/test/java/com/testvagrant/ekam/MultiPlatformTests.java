package com.testvagrant.ekam;

import com.testvagrant.ekam.commons.PageInitiator;
import com.testvagrant.ekam.mobile.screens.CalculatorScreen;
import com.testvagrant.ekam.testbase.EkamWebApiTest;
import com.testvagrant.ekam.testbase.EkamWebTest;
import com.testvagrant.ekam.web.pages.GooglePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static com.testvagrant.ekam.commons.ActivityInitiator.Activity;
import static com.testvagrant.ekam.commons.PageInitiator.WebPage;

public class MultiPlatformTests extends EkamWebTest {

    @Test(groups = "both")
    @Story("Abc")
    @Feature("Hello")
    @Epic("APC")
    public void searchTest() {
        GooglePage googlePage = WebPage(GooglePage.class);
        googlePage.onHomePage();
        googlePage.search("Calling from Ekam");
        CalculatorScreen activity = Activity().getInstance(CalculatorScreen.class);
        int res = activity.add(2, 3);
    }
}
