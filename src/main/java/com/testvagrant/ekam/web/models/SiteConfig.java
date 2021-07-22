package com.testvagrant.ekam.web.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SiteConfig {
  @Builder.Default private String url = "";

  @Builder.Default private String title = "";

  @Builder.Default private int wait = 30;
}
