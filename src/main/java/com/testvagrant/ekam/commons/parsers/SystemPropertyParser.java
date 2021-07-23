package com.testvagrant.ekam.commons.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Replace ${env:VARIABLE} with the system property */
public class SystemPropertyParser {

  public static String parse(String text) {
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
}
