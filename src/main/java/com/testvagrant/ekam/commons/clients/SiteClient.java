package com.testvagrant.ekam.commons.clients;

import com.google.inject.Inject;
import com.testvagrant.ekam.commons.annotations.Url;
import com.testvagrant.ekam.commons.exceptions.UnReachableSiteException;
import com.testvagrant.ekam.reports.ReportLogger;
import io.qameta.allure.Step;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

import java.io.IOException;

public class SiteClient {

  @Inject @Url private String url;

  private final OkHttpClient okHttpClient;

  public SiteClient() {
    okHttpClient = new OkHttpClient();
  }

  @Step("Checking if site is down")
  public boolean isSiteDown() {
    try {
      Request siteRequest = buildBasicRequest();
      ReportLogger.log("Creating a new site request" + siteRequest.toString());
      Response execute = okHttpClient.newCall(buildBasicRequest()).execute();
      return !execute.isSuccessful();
    } catch (IOException e) {
      ReportLogger.log("Failed to execute site request");
      e.printStackTrace();
    }
    return true;
  }

  public void terminateIfSiteIsDown() {
    try {
      if (isSiteDown()) {
        throw new UnReachableSiteException(url);
      }
      ReportLogger.log("Proceeding ahead as env is available");
    } catch (UnReachableSiteException e) {
      ReportLogger.log("Unable to reach site, terminating build");
      System.exit(1);
    }
  }

  private Request buildBasicRequest() {
    return new Builder().url(url).build();
  }
}
