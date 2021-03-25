package com.testvagrant.ekam.commons.listeners;

import com.testvagrant.ekam.commons.Injectors;
import com.testvagrant.ekam.commons.logs.LogWriter;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import java.util.List;

public class ReportListener implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        suites.forEach(suite -> {
            suite.getResults().entrySet().forEach(entry -> {
                entry.getValue().getTestContext().getPassedTests().getAllResults().forEach(iTestResult -> {
                    List<String> logs = Reporter.getOutput(iTestResult);
                    String logFolder = (String) suite.getAttribute(Injectors.LOG_FOLDER.getInjector());
                    LogWriter.writeLog(logFolder, iTestResult.getName(),logs);
                });
            });
        });
    }
}
