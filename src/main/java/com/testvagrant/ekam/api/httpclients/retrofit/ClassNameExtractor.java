package com.testvagrant.ekam.api.httpclients.retrofit;

public class ClassNameExtractor {
    public String extract(String className) {
        String[] split = className.split("<");
        return split[1].replaceAll(">", "").trim();
    }
}
