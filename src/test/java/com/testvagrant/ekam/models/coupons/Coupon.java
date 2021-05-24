package com.testvagrant.ekam.models.coupons;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    private String name;
    private String validity;
    private String maxDiscount;
}
