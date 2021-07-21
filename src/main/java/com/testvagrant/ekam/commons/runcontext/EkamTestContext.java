package com.testvagrant.ekam.commons.runcontext;

import com.testvagrant.ekam.commons.io.ResourcePaths;
import com.testvagrant.ekam.commons.models.EkamTest;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EkamTestContext {

  private EkamTest ekamTest;
  private String testFolder;

  @Builder.Default private List<TargetDetails> targets = new ArrayList<>();

  public EkamTestContext testPath(String className, String testName) {
    testFolder = ResourcePaths.getTestPath(className, testName);
    return this;
  }

  public EkamTestContext addTarget(TargetDetails target) {
    if (target != null) targets.add(target);
    return this;
  }
}
