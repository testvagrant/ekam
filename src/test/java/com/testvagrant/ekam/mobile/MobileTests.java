package com.testvagrant.ekam.mobile;

import com.testvagrant.ekam.mobile.pages.CalculatorActivity;
import com.testvagrant.ekam.testbase.EkamMobileTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.testvagrant.ekam.commons.ActivityInitiator.Activity;

public class MobileTests extends EkamMobileTest {

  @Test(groups = {"mobile"})
  public void additionTest() {
    CalculatorActivity activity = Activity().getInstance(CalculatorActivity.class);
    int res = activity.add(2, 3);
    Assert.assertEquals(res, 5);
  }

  @Test(groups = {"mobile"})
  public void additionTest1() {
    CalculatorActivity activity = Activity().getInstance(CalculatorActivity.class);
    int res = activity.add(2, 4);
    Assert.assertEquals(res, 6);
  }
}
