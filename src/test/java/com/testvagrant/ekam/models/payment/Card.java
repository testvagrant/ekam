package com.testvagrant.ekam.models.payment;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Card implements PaymentMode {
  private String cardNumber;
  private Expiry expiry;
  private String nameOnCard;
  private String cvv;

  @Override
  public Card getCard() {
    return this;
  }
}
