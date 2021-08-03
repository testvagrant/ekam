package com.testvagrant.ekam.commons.parsers;

import com.testvagrant.ekam.commons.io.GsonParser;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Optional;

import static com.testvagrant.ekam.commons.io.FileUtilities.fileUtils;

public class TestConfigParser {

  public <T> T loadFeed(String name, String env, Class<T> tClass) {
    Optional<File> feed = fileUtils().findResource(name + ".json", env);
    File file = feed.orElseThrow(() -> new RuntimeException("Cannot find feed " + name));
    return new GsonParser().deserialize(file.getPath(), tClass);
  }

  public <T> T loadFeed(String name, String env, Type tType) {
    Optional<File> feed = fileUtils().findResource(name + ".json", env);
    File file = feed.orElseThrow(() -> new RuntimeException("Cannot find feed " + name));
    return new GsonParser().deserialize(file.getPath(), tType);
  }
}
