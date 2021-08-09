package com.testvagrant.ekam.internal.executiontimeline.models;

import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/** Model representing EkamTest execution details */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EkamTestContext {

  private EkamTest ekamTest;
  private String testDirectory;
  @Builder.Default private List<TargetDetails> targets = new ArrayList<>();

  public void addTarget(TargetDetails target) {
    if (target != null) targets.add(target);
  }
}
