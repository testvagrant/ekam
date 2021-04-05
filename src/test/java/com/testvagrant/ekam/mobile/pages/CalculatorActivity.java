package com.testvagrant.ekam.mobile.pages;

import com.testvagrant.ekam.atoms.mobile.android.BaseActivity;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import org.openqa.selenium.By;

import java.util.Arrays;

public class CalculatorActivity extends BaseActivity {

  By op_add = queryById("op_add");
  By resultPreview = queryById("result_preview");
  private final String numKey = "digit_%s";

  @Screenshot
  public int add(int... numbers) {
    Arrays.stream(numbers)
        .forEach(
            num -> {
              Element(queryById(String.format(numKey, num))).click();
              Element(op_add).click();
            });

    return Integer.parseInt(Element(resultPreview).getTextValue());
  }
}
