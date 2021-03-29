package com.testvagrant.ekam.internal;

import com.google.inject.Inject;
import com.testvagrant.ekam.atoms.web.BasePage;
import com.testvagrant.ekam.atoms.web.BrowserManager;
import com.testvagrant.ekam.commons.annotations.Url;
import com.testvagrant.ekam.commons.models.Site;

public class LaunchPage extends BasePage {

  @Inject @Url String url;

  @Inject Site site;

  public void launch() {
    log("launches site " + url);
    browserManager.get(url);
  }

  public Site getSiteDetails() {
    Site siteDetails = site.toBuilder().title(browserManager.title()).build();
    log("verifies site details " + siteDetails.toString());
    return siteDetails;
  }
}
