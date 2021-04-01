package com.testvagrant.ekam.api.models;

import lombok.*;

@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder(toBuilder = true)
public class Status {
    private boolean verified;
    private int setCount;
    private String feedback;
}
