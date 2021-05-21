package com.testvagrant.ekam.commons.listeners;

import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.cache.TestContextCacheProvider;
import com.testvagrant.ekam.commons.exceptions.NoSuchKeyException;
import com.testvagrant.optimus.dashboard.models.dashboard.BuildOptions;
import com.testvagrant.optimus.dashboard.publishers.OptimusReportPublisher;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.util.List;

@SuppressWarnings("unchecked")
public class OptimusDashboardListener implements IReporter {

  @Override
  public void generateReport(
      List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
    if(Toggles.PUBLISH_TO_DASHBOARD.isOff()) return;
    getBuildOptions();
    OptimusReportPublisher optimusReportPublisher =
        new OptimusReportPublisher(SystemProperties.EKAM_SERVER_URL, getBuildOptions());
    optimusReportPublisher.publish();
  }


  private BuildOptions getBuildOptions() {
    String target = "";
    try {
      target = (String) TestContextCacheProvider.getInstance().get("target");
    } catch (NoSuchKeyException e) {
      target = "mobile";
    }
    return new BuildOptions(target);
  }
}
