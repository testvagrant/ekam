package com.testvagrant.ekam.models.payment;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Expiry {
    private String month;
    private String year;
}
