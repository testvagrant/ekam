package com.testvagrant.ekam.commons.models;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.testvagrant.ekam.commons.SystemProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@SuppressWarnings({"unchecked", "rawtypes"})
public class Locale {

  private LinkedTreeMap en;
  private LinkedTreeMap id;

  public <T> T getLocale(Class<T> tClass) {
    LinkedTreeMap map = localeMap().get(SystemProperties.LOCALE.toLowerCase());
    return getMapAsObj(map, tClass);
  }

  private Map<String, LinkedTreeMap> localeMap() {
    Map<String, LinkedTreeMap> localeMap = new LinkedHashMap<>();
    localeMap.put("en", en);
    localeMap.put("id", id);
    return localeMap;
  }

  private <T> T getMapAsObj(LinkedTreeMap map, Class<T> tClass) {
    Gson gson = new Gson();
    String objJson = gson.toJson(map);
    return gson.fromJson(objJson, tClass);
  }
}
