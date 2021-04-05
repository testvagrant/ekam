package com.testvagrant.ekam.internal;

import static com.testvagrant.ekam.commons.PageInitiator.Page;

public class Launcher {

  public void launch() {
    LaunchPage launchPage = Page().getInstance(LaunchPage.class);
    launchPage.launch();
    launchPage.getSiteDetails().assertThatSiteIsUp();
  }
}
