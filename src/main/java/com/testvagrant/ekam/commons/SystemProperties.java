package com.testvagrant.ekam.commons;

public final class SystemProperties {
  // configs
  public static final String DB_CONFIG = System.getProperty("dbConfig", "dbConfig");
  public static final String API_CONFIG = System.getProperty("apiConfig", "apiConfig");

  // Toggles
  public static final Boolean TIMELINE = Boolean.valueOf(System.getProperty("timeline", "true"));
  public static final Boolean MOCK = Boolean.valueOf(System.getProperty("mock", "false"));
  public static final Boolean LOGS = Boolean.valueOf(System.getProperty("logs", "false"));
  public static final Boolean SWITCH_VIEW =
      Boolean.valueOf(System.getProperty("switchView", "false"));

  // others
  public static final String TAGS = System.getProperty("tags", "smoke");
  public static final String LOCALE = System.getProperty("locale", "en");
  public static final String CLIENT = System.getProperty("client", "retrofit").toUpperCase();
  public static final String LANGUAGE = System.getProperty("language", "en");
  public static final String JOB_URL = System.getenv("CI_JOB_URL");
  public static final String SLACK_CHANNEL_TOKEN = System.getenv("CHANNEL_TOKEN");
  public static final boolean SLACK_NOTIF =
      Boolean.parseBoolean(System.getProperty("slackNotif", String.valueOf(false)));
  public static final boolean SLACK_NOTIFY_ME_EVERYTIME =
      Boolean.parseBoolean(System.getProperty("notifyMeEverytime", String.valueOf(false)));
  public static final String OPTIMUS_SERVER_URL =
      System.getProperty("optimusServerUrl", "http://localhost:8090/");
}
