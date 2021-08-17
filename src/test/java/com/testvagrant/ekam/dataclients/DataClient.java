package com.testvagrant.ekam.dataclients;

import com.testvagrant.ekam.commons.data.DataSetsClient;

public class DataClient extends DataSetsClient {

  public <T> T get(String key, Class<T> tClass) {
    return getValue(key, tClass, false);
  }
}
