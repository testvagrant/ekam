package com.testvagrant.ekam.api.httpclients.retrofit;

import retrofit2.Call;
import retrofit2.Invocation;

public class CallTypeFinder {

    private ClassNameExtractor classNameExtractor;
    public CallTypeFinder() {
        classNameExtractor = new ClassNameExtractor();
    }

    public <T> Class getType(Call call) {
        Invocation tag = call.request().tag(Invocation.class);
        String typeName = tag.method().getGenericReturnType().getTypeName();
        String type = classNameExtractor.extract(typeName);
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {

        }
        throw new RuntimeException("Cannot create type for call " + typeName);
    }
}
