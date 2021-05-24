package com.testvagrant.ekam.models.bagDetails;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDetails {
    private String expectedDate;
    private String shippingMode;
}
