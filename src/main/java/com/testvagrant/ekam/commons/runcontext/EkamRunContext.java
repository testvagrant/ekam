package com.testvagrant.ekam.commons.runcontext;

import com.testvagrant.ekam.commons.io.ResourcePaths;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import lombok.*;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EkamRunContext {

  private WebDriver webDriver;
  private String testFolder;

  @Builder.Default private List<TargetDetails> targets = new ArrayList<>();

  public EkamRunContext testPath(String className, String testName) {
    testFolder = ResourcePaths.getTestPath(className, testName);
    return this;
  }

  public EkamRunContext addTarget(TargetDetails target) {
    targets.add(target);
    return this;
  }

  public EkamRunContext addTarget(TargetDetails... target) {
    targets.addAll(Arrays.asList(target));
    return this;
  }
}
