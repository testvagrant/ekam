package com.testvagrant.ekam.internal;

import com.google.inject.Inject;
import com.testvagrant.ekam.atoms.web.BasePage;
import com.testvagrant.ekam.commons.annotations.Url;
import com.testvagrant.ekam.commons.models.Site;

import static com.testvagrant.ekam.atoms.web.BrowserManager.BrowserManager;

public class LaunchPage extends BasePage {

  @Inject @Url String url;

  @Inject Site site;

  public void launch() {
    log("launches site " + url);
    BrowserManager().get(url);
  }

  public Site getSiteDetails() {
    Site siteDetails = site.toBuilder().title(BrowserManager().title()).build();
    log("verifies site details " + siteDetails.toString());
    return siteDetails;
  }
}
