package com.testvagrant.ekam.commons.parsers.testfeed;

import com.testvagrant.ekam.commons.io.FileFinder;
import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.io.ResourcePaths;
import com.testvagrant.ekam.commons.models.server.EkamServerFlag;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.IOSServerFlag;
import io.appium.java_client.service.local.flags.ServerArgument;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class ServerArgumentParser {

  private List<String> serverArgs;

  public ServerArgumentParser(String serverArgsName) {
    File file = new FileFinder(ResourcePaths.TEST_RESOURCES, "").find(serverArgsName, ".json");
    serverArgs = new GsonParser().deserialize(file.getPath(), List.class);
  }

  public ServerArgumentParser(List<String> serverArgs) {
    this.serverArgs = serverArgs;
  }

  public Map<ServerArgument, String> getServerArgumentsMap() {
    return addServerArguments(
        GeneralServerFlag.values(),
        IOSServerFlag.values(),
        AndroidServerFlag.values(),
        EkamServerFlag.values());
  }

  private Map<ServerArgument, String> addServerArguments(ServerArgument[]... values) {
    Map<ServerArgument, String> serverArgumentsMap = new HashMap<>();

    Map<String, ServerArgument> serverFlags =
        Arrays.stream(values)
            .flatMap(Arrays::stream)
            .collect(Collectors.toMap(ServerArgument::getArgument, item -> item));

    serverArgs.parallelStream()
        .forEach(
            arg -> {
              String[] serverArg =
                  arg.contains("=") ? arg.trim().split("=") : arg.trim().split(" ");

              if (serverFlags.containsKey(serverArg[0])) {
                ServerArgument argument = serverFlags.get(serverArg[0]);
                String value = serverArg.length == 2 ? serverArg[1] : "";
                serverArgumentsMap.put(argument, value);
              }
            });

    return serverArgumentsMap;
  }
}
