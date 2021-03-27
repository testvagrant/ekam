package com.testvagrant.ekam.api.models;

import lombok.*;

import java.util.Date;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
public class CatFacts {
    private Status status;
    private String type;
    private boolean deleted;
    private String _id;
    private String user;
    private String text;
    private int __v;
    private String source;
    private Date updatedAt;
    private Date createdAt;
    private boolean used;

}
