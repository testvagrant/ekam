package com.testvagrant.ekam.internal;

import com.testvagrant.ekam.commons.PageInitiator;

public class Launcher {

  public void launch() {
    LaunchPage launchPage = PageInitiator.getWebPage(LaunchPage.class);
    launchPage.launch();
    launchPage.getSiteDetails().assertThatSiteIsUp();
  }
}
