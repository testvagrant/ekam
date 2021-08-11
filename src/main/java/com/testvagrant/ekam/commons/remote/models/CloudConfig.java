package com.testvagrant.ekam.commons.remote.models;

import com.testvagrant.ekam.commons.parsers.SystemPropertyParser;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CloudConfig {
  private String username;
  private String accessKey;
  private String hub;
  @Builder.Default private String apiHost = "";
  @Builder.Default private String url = "";
  @Builder.Default private String protocol = "https";

  public CloudConfig parseSystemProperties() {
    setUsername(SystemPropertyParser.parse(getUsername()));
    setAccessKey(SystemPropertyParser.parse(getAccessKey()));
    setApiHost(SystemPropertyParser.parse(getApiHost()));
    setHub(SystemPropertyParser.parse(getHub()));
    setUrl(SystemPropertyParser.parse(getUrl()));
    setProtocol(SystemPropertyParser.parse(getProtocol()));
    return this;
  }

  @Override
  public String toString() {
    return "{"
        + "\"username\":\""
        + username
        + "\""
        + ", \"accessKey\":\""
        + accessKey
        + "\""
        + ", \"hub\":\""
        + hub
        + "\""
        + ", \"apiHost\":\""
        + apiHost
        + "\""
        + ", \"url\":\""
        + url
        + "\""
        + ", \"protocol\":\""
        + protocol
        + "\""
        + "}}";
  }
}
