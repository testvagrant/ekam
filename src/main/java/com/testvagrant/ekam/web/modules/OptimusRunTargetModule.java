package com.testvagrant.ekam.web.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.optimus.core.screenshots.OptimusRunContext;
import com.testvagrant.optimus.core.screenshots.OptimusRunTarget;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

public class OptimusRunTargetModule extends AbstractModule {
    private OptimusRunContext optimusRunContext;
    public OptimusRunTargetModule(WebDriver webDriver, ITestResult iTestResult) {
        optimusRunContext = OptimusRunContext.builder().webDriver(webDriver).build()
                .testPath(iTestResult.getTestClass().getName(), iTestResult.getName());
    }

    @Override
    protected void configure() {
        OptimusRunTarget optimusRunTarget = new OptimusRunTarget(optimusRunContext);
        bind(OptimusRunTarget.class).toInstance(optimusRunTarget);
    }

}
