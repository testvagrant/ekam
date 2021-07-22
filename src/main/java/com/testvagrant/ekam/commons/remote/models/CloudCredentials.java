package com.testvagrant.ekam.commons.remote.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CloudCredentials {
  private String username;
  private String accessKey;
}
