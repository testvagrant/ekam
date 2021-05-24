package com.testvagrant.ekam.models.customerDetails;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MyCliqWallet {
  private String amount;
}
