package com.testvagrant.ekam.mobile;

import com.testvagrant.ekam.mobile.screens.transferGo.LaunchScreen;
import com.testvagrant.ekam.testbase.EkamMobileTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.testvagrant.ekam.commons.ActivityInitiator.Activity;

public class TransferGoTests extends EkamMobileTest {

  @Test(groups = {"mobile", "transfergo"})
  public void shouldValidateRatesMatchInRange() {
    LaunchScreen launchScreen = Activity().getInstance(LaunchScreen.class);
    Double receivingAmount =
        launchScreen
            //
            .checkRates()
            .selectSendingFromCountry("France")
            .setSendingFrom("100.00")
            .selectReceivingInCountry("India")
            .getReceivingInAmount();

    Assert.assertEquals(receivingAmount, 8800, 100);
  }

//  @Test(groups = {"mobile","transfergo"})
  public void shouldValidateRatesMatchInRange1() {
    LaunchScreen launchScreen = Activity().getInstance(LaunchScreen.class);
    Double receivingAmount =
            launchScreen
                    //
                    .checkRates()
                    .selectSendingFromCountry("France")
                    .setSendingFrom("100.00")
                    .selectReceivingInCountry("India")
                    .getReceivingInAmount();

    Assert.assertEquals(receivingAmount, 8800, 100);
  }
}
