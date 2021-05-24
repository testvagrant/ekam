package com.testvagrant.ekam.models;

import com.testvagrant.ekam.models.bagDetails.Bag;
import com.testvagrant.ekam.models.coupons.Coupon;
import com.testvagrant.ekam.models.customerDetails.Address;
import com.testvagrant.ekam.models.customerDetails.Credentials;
import com.testvagrant.ekam.models.payment.PaymentMode;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  private Credentials credentials;
  private Bag bag;
  private Address billingAddress;
  private Address shippingAddress;
  private PaymentMode card;
  private Bag bagDetails;
  private Coupon coupon;
}
