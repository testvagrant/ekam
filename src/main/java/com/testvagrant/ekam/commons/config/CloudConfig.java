package com.testvagrant.ekam.commons.config;

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
  @Builder.Default private String url = "";
  @Builder.Default private String protocol = "https";

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
        + ", \"url\":\""
        + url
        + "\""
        + ", \"protocol\":\""
        + protocol
        + "\""
        + "}}";
  }
}
