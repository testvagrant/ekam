package com.testvagrant.ekam.commons.models.web;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class WebTestFeed {
  @Builder.Default private Map<String, Object> desiredCapabilities = new HashMap<>();
  @Builder.Default private List<String> arguments = new ArrayList<>();
  @Builder.Default private Map<String, Object> preferences = new HashMap<>();
  @Builder.Default private List<String> extensions = new ArrayList<>();
  @Builder.Default private Map<String, Object> experimentalOptions = new HashMap<>();
}
