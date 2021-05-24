package com.testvagrant.ekam.models.customerDetails;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
  private String email ="default@email.com";
  private String password;

  private String mobile;
  private String otp;

  @Override
  public String toString() {
    return "{"
            + "\"email\":\"" + email + "\""
            + ", \"password\":\"" + password + "\""
            + ", \"mobile\":\"" + mobile + "\""
            + ", \"otp\":\"" + otp + "\""
            + "}";
  }
}
