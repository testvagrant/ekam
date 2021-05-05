package com.testvagrant.ekam.commons.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.optimus.core.models.TargetDetails;
import com.testvagrant.optimus.core.screenshots.OptimusRunContext;
import com.testvagrant.optimus.core.screenshots.OptimusRunTarget;
import com.testvagrant.optimus.dashboard.StepRecorder;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

public class OptimusRunTargetModule extends AbstractModule {
    private OptimusRunContext optimusRunContext;
    private ITestResult iTestResult;

    public OptimusRunTargetModule(WebDriver webDriver, ITestResult iTestResult, TargetDetails targetDetails) {
        this.iTestResult = iTestResult;
        optimusRunContext = OptimusRunContext.builder().webDriver(webDriver).build()
                .addTarget(targetDetails)
                .testPath(iTestResult.getTestClass().getName(), iTestResult.getName());
    }

    @Override
    protected void configure() {
        OptimusRunTarget optimusRunTarget = new OptimusRunTarget(optimusRunContext);
        bind(OptimusRunTarget.class).toInstance(optimusRunTarget);

        StepRecorder stepRecorder = new StepRecorder(iTestResult.getTestClass().getName(), iTestResult.getName());
        bind(StepRecorder.class).toInstance(stepRecorder);

    }

}
