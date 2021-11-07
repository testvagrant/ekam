package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.remote.ConfigLoader;
import com.testvagrant.ekam.commons.remote.RemoteUrlBuilder;
import com.testvagrant.ekam.commons.remote.models.CloudConfig;
import com.testvagrant.ekam.devicemanager.devicefinder.BrowserStackDeviceFinder;
import com.testvagrant.ekam.devicemanager.models.DeviceType;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.CapabilityMapper;
import com.testvagrant.ekam.mobile.configparser.MobileConfigParser;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.tuple.Triple;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class PerfectoDriver {

    private final DesiredCapabilities desiredCapabilities;
    private final CloudConfig cloudConfig;
    private final MobileConfigParser mobileConfigParser;

    public PerfectoDriver(MobileConfigParser mobileConfigParser) {
        this.mobileConfigParser = mobileConfigParser;
        this.cloudConfig = new ConfigLoader().loadConfig("perfecto");
        this.desiredCapabilities = mobileConfigParser.getDesiredCapabilities();
    }

    public Triple<URL, DesiredCapabilities, TargetDetails> buildRemoteMobileConfig() {
        URL url = RemoteUrlBuilder.build(cloudConfig);
        Map<String, Object> browserStackCaps = mapToPerfectoDesiredCaps();
        DesiredCapabilities updatedCapabilities =
                desiredCapabilities.merge(new DesiredCapabilities(browserStackCaps));
        ekamLogger().info("Building remote capabilities {}", updatedCapabilities);
        return Triple.of(url, updatedCapabilities, findTarget());
    }

    private String uploadApp() {
        if (!mobileConfigParser.getMobileConfig().isRemote()) return "";
        String app =
                (String)
                        mobileConfigParser.getDesiredCapabilities().getCapability(MobileCapabilityType.APP);
        boolean isAppPresent = !Objects.isNull(app) && !app.isEmpty();
        if (!mobileConfigParser.getMobileConfig().isUploadApp() || !isAppPresent) return "";
        return RemoteDriverUploadFactory.uploadUrl(mobileConfigParser.getMobileConfig().getHub(), app);
    }

    private TargetDetails findTarget() {
        return TargetDetails.builder()
                .name("Perfecto Device")
                .platform(EkamSupportedPlatforms.ANDROID)
                .runsOn(DeviceType.DEVICE)
                .udid(UUID.randomUUID().toString())
                .platformVersion("8.0")
                .build();
    }

    private Map<String, Object> mapToPerfectoDesiredCaps() {
        Map<String, Object> capsMap = new HashMap<>();
        capsMap.put("securityToken", cloudConfig.getAccessKey());
        return capsMap;
    }
}
