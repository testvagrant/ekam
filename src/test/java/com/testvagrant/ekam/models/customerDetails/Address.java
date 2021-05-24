package com.testvagrant.ekam.models.customerDetails;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String pinCode;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String state;
    private String landmark;
    private String phoneNumber;
    private boolean defaultAddress;
}
