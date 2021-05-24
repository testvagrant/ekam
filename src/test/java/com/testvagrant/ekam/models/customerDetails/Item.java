package com.testvagrant.ekam.models.customerDetails;

import com.testvagrant.ekam.models.bagDetails.DeliveryDetails;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Item {
  private String id;
  private String name;
  private String price;
  private int quantity;
  private DeliveryDetails deliveryDetails;
}
