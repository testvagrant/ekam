package com.testvagrant.ekam.commons.parsers.testfeed;

import com.testvagrant.ekam.commons.io.FileFinder;
import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.io.ResourcePaths;

import java.io.File;

public class TestConfigParser {

  public <T> T loadFeed(String name, String env, Class<T> tClass) {
    File file = new FileFinder(ResourcePaths.TEST_RESOURCES, env).find(name, ".json");
    return new GsonParser().deserialize(file.getPath(), tClass);
  }
}
