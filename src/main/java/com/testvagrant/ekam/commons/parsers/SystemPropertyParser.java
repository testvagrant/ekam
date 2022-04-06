package com.testvagrant.ekam.commons.parsers;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

/** Replace ${env:VARIABLE} with the system property */
public class SystemPropertyParser {

  public static String parse(String text) {
    if (Objects.isNull(text) || text.isEmpty()) return text;
    List<String> allMatches = new ArrayList<>();
    Pattern p = Pattern.compile("\\$\\{env:(.*?)}");
    Matcher m = p.matcher(text);
    while (m.find()) {
      allMatches.add(m.group().replaceAll("\\$\\{env:", "").replaceAll("}", ""));
    }

    for (String allMatch : allMatches) {
      String replaceText = String.format("\\$\\{env:%s}", allMatch);
      String replaceValue = System.getProperty(allMatch, "");
      text = text.replaceAll(replaceText, replaceValue);
    }
    ekamLogger().info("Parsing system property {}", text);
    return text;
  }

  public static Map<String, String> parse(Map<String, String> keyValuePair) {
    Map<String, String> updatedPair = new HashMap<>();
    keyValuePair.forEach(
        (k, v) -> {
          updatedPair.put(k, parse(v));
        });

    return updatedPair;
  }

  public static ArrayList<String> parse(ArrayList<String>capabilities){
    ArrayList<String>parsedCapabilities=new ArrayList<>();
    for (String capability : capabilities)
      parsedCapabilities.add(parse(capability));
    return parsedCapabilities;
  }
}
