package com.testvagrant.ekam.models.bagDetails;

import com.testvagrant.ekam.models.customerDetails.Item;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Bag {
    private List<Item> items;
    private BagSummary bagSummary;
    private String appliedCoupon;
}
