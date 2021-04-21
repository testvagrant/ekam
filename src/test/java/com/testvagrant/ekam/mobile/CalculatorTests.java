package com.testvagrant.ekam.mobile;

import com.testvagrant.ekam.mobile.screens.CalculatorScreen;
import com.testvagrant.ekam.testbase.EkamMobileTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.testvagrant.ekam.commons.ActivityInitiator.Activity;

public class CalculatorTests extends EkamMobileTest {

  @Test(groups = {"mobile"})
  public void additionTest() {
    CalculatorScreen activity = Activity().getInstance(CalculatorScreen.class);
    int res = activity.add(2, 3);
    Assert.assertEquals(res, 5);
  }

  @Test(groups = {"mobile"})
  public void additionTest1() {
    CalculatorScreen activity = Activity().getInstance(CalculatorScreen.class);
    int res = activity.add(2, 4);
    Assert.assertEquals(res, 6);
  }
}
