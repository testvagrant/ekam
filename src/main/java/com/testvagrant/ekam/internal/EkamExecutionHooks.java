package com.testvagrant.ekam.internal;

import com.google.inject.Guice;
import com.testvagrant.ekam.commons.parsers.testfeed.MobileConfigParser;
import com.testvagrant.ekam.config.EkamConfigModule;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.mobile.remote.RemoteDriverUploadFactory;
import io.appium.java_client.remote.MobileCapabilityType;

import java.util.Objects;

import static com.testvagrant.ekam.internal.EkamAssetsProvider.EkamAssets.Remote.APP_UPLOAD_URL;
import static com.testvagrant.ekam.internal.EkamAssetsProvider.ekamAssets;

public class EkamExecutionHooks {

  private final EkamConfig ekam;
  private final MobileConfigParser mobileConfigParser;

  public EkamExecutionHooks() {
    this.ekam = Guice.createInjector(new EkamConfigModule()).getInstance(EkamConfig.class);
    mobileConfigParser = new MobileConfigParser(ekam.getMobile());
  }

  public void apply() {
    uploadApp();
  }

  public void uploadApp() {
    if (!ekam.getMobile().isRemote()) return;
    ekamAssets().put(APP_UPLOAD_URL, "");
    String app =
        (String)
            mobileConfigParser.getDesiredCapabilities().getCapability(MobileCapabilityType.APP);
    boolean isAppPresent = !Objects.isNull(app) && !app.isEmpty();
    if (!ekam.getMobile().isUploadApp() || !isAppPresent) return;
    ekamAssets()
        .put(APP_UPLOAD_URL, RemoteDriverUploadFactory.uploadUrl(ekam.getMobile().getHub(), app));
  }
}
