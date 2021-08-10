package com.testvagrant.ekam.internal.injectors;

import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.modules.ModulesLibrary;

public class EkamAPIInjector extends EkamInjector {
  public EkamAPIInjector(EkamTest ekamTest, EkamConfig ekamConfig) {
    super(ekamTest, ekamConfig);
  }

  /** Creates API Injector binding APIHostsModule, StepRecorderModule */
  public void create() {
    createInjector(new ModulesLibrary().baseModules());
    createTargetJson();
  }
}
