package com.testvagrant.ekam.commons.listeners;

import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.optimus.dashboard.publishers.OptimusReportPublisher;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.util.List;

public class OptimusDashboardListener implements IReporter {
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        OptimusReportPublisher optimusReportPublisher = new OptimusReportPublisher(SystemProperties.OPTIMUS_SERVER_URL);
        optimusReportPublisher.publish();
    }
}