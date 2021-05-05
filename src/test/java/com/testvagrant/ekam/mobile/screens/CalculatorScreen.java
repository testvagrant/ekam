package com.testvagrant.ekam.mobile.screens;

import com.testvagrant.ekam.atoms.mobile.MobileScreen;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;

import java.util.Arrays;

public class CalculatorScreen extends MobileScreen {

  By op_add = queryById("op_add");
  By resultPreview = queryById("result_preview");
  private final String numKey = "digit_%s";

  @Step(description = "Calculator")
  public int add(int... numbers) {
      Capabilities capabilities = driver.getCapabilities();
      System.out.println(capabilities);
      Arrays.stream(numbers)
        .forEach(
            num -> {
              element(queryById(String.format(numKey, num))).click();
              element(op_add).click();
            });

    return Integer.parseInt(element(resultPreview).getTextValue());
  }
}
