package com.testvagrant.ekam.internal;


import com.testvagrant.ekam.commons.PageInitiator;

import static com.testvagrant.ekam.commons.PageInitiator.WebPage;

public class Launcher {

  public void launch() {
    LaunchPage launchPage = WebPage(LaunchPage.class);
    launchPage.launch();
    launchPage.getSiteDetails().assertThatSiteIsUp();
  }
}
