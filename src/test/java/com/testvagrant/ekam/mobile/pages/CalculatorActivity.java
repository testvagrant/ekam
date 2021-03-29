package com.testvagrant.ekam.mobile.pages;

import com.testvagrant.ekam.atoms.mobile.android.BaseActivity;
import org.openqa.selenium.By;

import java.util.Arrays;

public class CalculatorActivity extends BaseActivity {

  By delete = queryById("del");
  By op_divide = queryById("op_div");
  By op_mul = queryById("op_mul");
  By op_subtract = queryById("op_sub");
  By op_add = queryById("op_add");
  By eq = queryById("eq");
  By dec_point = queryById("dec_point");
  By formula = queryById("formula");
  By resultPreview = queryById("result_preview");
  private String numKey = "digit_%s";

  public int add(int... nums) {
    Arrays.stream(nums)
        .forEach(
            num -> {
              Element(queryById(String.format(numKey, num))).click();
              Element(op_add).click();
            });

    return Integer.parseInt(Element(resultPreview).getTextValue());
  }
}
