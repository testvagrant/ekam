package com.testvagrant.ekam.commons;

import com.testvagrant.ekam.web.RunMode;
import com.testvagrant.ekam.web.drivers.Browser;

public final class SystemProperties {
    public static final String DEFAULT_ENV = System.getProperty("defaultEnv", "staging");
    public static final String TESTFEED = System.getProperty("testFeed", "paylater_nightly_local");
    public static final String ENV = System.getProperty("env", DEFAULT_ENV);
    public static final String LOCALE = System.getProperty("locale", "en");
    public static final Boolean TIMELINE = Boolean.valueOf(System.getProperty("timeline", "true"));
    public static final Boolean MOCK = Boolean.valueOf(System.getProperty("mock", "false"));
    public static final Boolean LOGS = Boolean.valueOf(System.getProperty("logs", "false"));
    public static final String HUB = System.getProperty("hub", "kobiton").toUpperCase();
    public static final Target TARGET = Target.valueOf(System.getProperty("target", "local").toUpperCase());
    public static final RunMode RUN_MODE = RunMode.valueOf(System.getProperty("runMode", "headless").toUpperCase());
    public static final Boolean SWITCH_VIEW = Boolean.valueOf(System.getProperty("switchView", "false"));
    public static final String CLIENT = System.getProperty("client", "retrofit").toUpperCase();
    public static final String TAGS = System.getProperty("tags", "smoke");
    public static final String LANGUAGE = System.getProperty("language", "en");
    public static final String PAGES_URL = System.getenv("CI_PAGES_URL");
    public static final String SLACK_CHANNEL_TOKEN = System.getenv("CHANNEL_TOKEN");
    public static final boolean SLACK_NOTIF = Boolean.parseBoolean(System.getProperty("slackNotif", String.valueOf(false)));
    public static final String BROWSER = System.getProperty("browser", Browser.FIREFOX.name().toLowerCase());
}
