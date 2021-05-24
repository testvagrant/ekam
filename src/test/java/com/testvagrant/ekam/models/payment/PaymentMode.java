package com.testvagrant.ekam.models.payment;

public interface PaymentMode {
  default Card getCard() {
    throw new UnsupportedOperationException();
  }
}
