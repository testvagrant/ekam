package com.testvagrant.ekam.mobile.screens.transferGo;

import com.testvagrant.ekam.atoms.mobile.MobileScreen;
import com.testvagrant.ekam.atoms.mobile.Textbox;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TransferScreen extends MobileScreen {

  private final By sendingFromTextboxSelector = queryById("loe_calculator_sending_amount_input_field");
  private final By receivingInTextboxSelector = queryById("loe_calculator_receiving_amount_input_field");
  private final By sendingFromCountrySelector = queryById("loe_calculator_sending_from_country_field");
  private final By receivingInCountrySelector = queryById("loe_calculator_receiving_in_country_field");
  private final By searchTextBoxSelector = queryById("sheet_adapter_view_search_field");
  private final By choose_different_country = queryAndroidTextViewByText("Choose different country");

  @Step("Select sending from country")
  public TransferScreen selectSendingFromCountry(String country) {
    Textbox receivingIn = textbox(receivingInTextboxSelector);
    String receivingInValue = receivingIn.getTextValue();

    element(sendingFromCountrySelector).click();
    searchAndSelectCountry(country);

    receivingIn.waitUntilTextNotToBe(receivingInValue);
    return this;
  }

  public TransferScreen selectReceivingInCountry(String country) {
    Textbox receivingIn = textbox(receivingInTextboxSelector);
    String receivingInValue = receivingIn.getTextValue();

    element(receivingInCountrySelector).click();
    element(choose_different_country).click();
    searchAndSelectCountry(country);

    receivingIn.waitUntilTextNotToBe(receivingInValue);
    return this;
  }

  public TransferScreen setSendingFrom(String amountToBeSent) {
    Textbox sendingFrom = textbox(sendingFromTextboxSelector);
    Textbox receivingIn = textbox(receivingInTextboxSelector);
    receivingIn.waitUntilTextNotToBe("0.00");

    String initialValue = sendingFrom.getTextValue().trim();

    if (!amountToBeSent.equals(initialValue)) {
      String receivingInValue = receivingIn.getTextValue();
      clearValueAndSetText(sendingFromTextboxSelector, amountToBeSent);
      receivingIn.waitUntilTextNotToBe(receivingInValue);
    }

    return this;
  }

  public Double getReceivingInAmount() {
    String rate = textbox(receivingInTextboxSelector).getTextValue().trim();
    if (!rate.isEmpty()) {
      return Double.parseDouble(rate.replace(" ", ""));
    }

    return 0.00;
  }

  @Screenshot
  private void searchAndSelectCountry(String country) {
    textbox(searchTextBoxSelector).setText(country);
    element(queryAndroidTextViewByText(country)).click();
  }


  private void clearValueAndSetText(By locator, String value) {
    element(locator).longPress();
    androidDeviceDriver.sendKeys(value);
  }
}
