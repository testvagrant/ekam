package com.testvagrant.ekam.models.bagDetails;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BagSummary {
    private String totalAmount;
    private String shippingMode; // TODO: Identify all shipping modes: Ex: FREE
    private String subTotal;
    private String productDiscount;
    private String couponDiscount;
    private String totalSavings;
    private String total;
}
