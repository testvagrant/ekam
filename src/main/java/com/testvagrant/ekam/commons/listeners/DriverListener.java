package com.testvagrant.ekam.commons.listeners;

import com.testvagrant.ekam.commons.Injectors;
import com.testvagrant.ekam.commons.logs.LogWriter;
import com.testvagrant.optimus.dashboard.OptimusTestNGBuildGenerator;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class DriverListener implements ISuiteListener {

  @Override
  public void onStart(ISuite suite) {
    OptimusTestNGBuildGenerator testNGBuildGenerator = new OptimusTestNGBuildGenerator();
    testNGBuildGenerator.startBuild();
    suite.setAttribute("buildGenerator", testNGBuildGenerator);
    String logFolder = new LogWriter().createLogFolder();
    suite.setAttribute(Injectors.LOG_FOLDER.getInjector(), logFolder);
  }

  @Override
  public void onFinish(ISuite suite) {
    OptimusTestNGBuildGenerator testNGBuildGenerator =
        (OptimusTestNGBuildGenerator) suite.getAttribute("buildGenerator");
    testNGBuildGenerator.endBuild();
    testNGBuildGenerator.generate();
  }
}
