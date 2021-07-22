package com.testvagrant.ekam.mobile.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileTestFeed {
  @Builder.Default private String appDir = "app";

  @Builder.Default private List<Map<String, Object>> desiredCapabilities = new ArrayList<>();

  @Builder.Default private List<String> serverArguments = new ArrayList<>();
}
