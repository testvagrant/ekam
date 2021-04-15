package com.testvagrant.ekam.atoms.mobile.android;

import com.testvagrant.ekam.atoms.mobile.QueryFunctions;
import org.openqa.selenium.By;

public interface AndroidQueryFunctions extends QueryFunctions {
  default By queryAndroidTextViewByText(String text) {
    return queryByText("android.widget.TextView", text);
  }

  default By queryAndroidTextViewByAttribute(String text) {
    return queryByIgnoreSpaceAndCase("android.widget.TextView", text);
  }
}
