package com.testvagrant.ekam.commons.testContext;

import com.google.common.base.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EkamTestContext {
  private String featureName;
  private String testName;

  @Override
  public int hashCode() {
    return Objects.hashCode(this.featureName, this.testName);
  }
}
