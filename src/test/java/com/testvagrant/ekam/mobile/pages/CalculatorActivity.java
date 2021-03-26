package com.testvagrant.ekam.mobile.pages;

import com.testvagrant.ekam.atoms.base.BaseActivity;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

public class CalculatorActivity extends BaseActivity {

    @AndroidFindBy(id = "del")
    private MobileElement delete;

    @AndroidFindBy(id = "op_div")
    private MobileElement op_divide;

    @AndroidFindBy(id = "op_mul")
    private MobileElement op_mul;

    @AndroidFindBy(id = "op_sub")
    private MobileElement op_subtract;

    @AndroidFindBy(id = "op_add")
    private MobileElement op_add;

    @AndroidFindBy(id = "eq")
    private MobileElement eq;

    @AndroidFindBy(id = "dec_point")
    private MobileElement dec_point;

    @AndroidFindBy(id = "formula")
    private MobileElement formula;

    @AndroidFindBy(id = "result_preview")
    private MobileElement resultPreview;

    private String numKey = "digit_%s";

    public int add(int... nums) {
        Arrays.stream(nums).forEach(num -> {
           click(getNumElement(num));
           click(op_add);
        });
        return Integer.parseInt(text(resultPreview));
    }

    private WebElement getNumElement(int num) {
        return driver.findElement(By.id(String.format(numKey, num)));
    }
}
