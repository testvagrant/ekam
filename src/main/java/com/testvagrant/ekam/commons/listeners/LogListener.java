package com.testvagrant.ekam.commons.listeners;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.Injectors;
import com.testvagrant.ekam.commons.logs.LogWriter;
import com.testvagrant.ekam.web.modules.SiteModule;
import com.testvagrant.optimus.dashboard.OptimusTestNGBuildGenerator;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.time.LocalDateTime;

public class LogListener implements ISuiteListener {

  @Override
  public void onStart(ISuite suite) {
    OptimusTestNGBuildGenerator testNGBuildGenerator = new OptimusTestNGBuildGenerator();
    testNGBuildGenerator.startBuild();
    suite.setAttribute("buildGenerator", testNGBuildGenerator);
    Injector logInjector =
        suite.getParentInjector().createChildInjector(new SiteModule());
    String logFolder = logInjector.getInstance(LogWriter.class).createLogFolder();
    suite.setAttribute(Injectors.LOG_FOLDER.getInjector(), logFolder);
  }

  @Override
  public void onFinish(ISuite suite) {
    OptimusTestNGBuildGenerator testNGBuildGenerator = (OptimusTestNGBuildGenerator) suite.getAttribute("buildGenerator");
    testNGBuildGenerator.endBuild();
    testNGBuildGenerator.generate();
  }
}
