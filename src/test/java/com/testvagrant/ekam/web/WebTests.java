package com.testvagrant.ekam.web;

import com.google.inject.Inject;
import com.testvagrant.ekam.api.httpclients.HttpClient;
import com.testvagrant.ekam.testbase.EkamWebTest;
import com.testvagrant.ekam.web.pages.GooglePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static com.testvagrant.ekam.commons.PageInitiator.getWebPage;

public class WebTests extends EkamWebTest {

    @Test
    @Story("Abc")
    @Feature("Hello")
    @Epic("APC")
    public void searchTest() {
        GooglePage googlePage = getWebPage(GooglePage.class);
        googlePage.search("Calling from Ekam");
    }

}
