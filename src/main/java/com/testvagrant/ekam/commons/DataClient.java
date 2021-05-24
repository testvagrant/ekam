package com.testvagrant.ekam.commons;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.testvagrant.optimus.commons.filehandlers.GsonParser;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class DataClient {

    public <T, Q> T unBox(Q treeMap, Class<T> tClass) {
        String value = getJsonString(treeMap);
        return deserialize(value, tClass);
    }

    public <T, Q> List<T> unBoxAsList(Q treeMap, Class<T> tClass) {
        String value = getJsonString(treeMap);
        Type type = TypeToken.getParameterized(List.class, tClass).getType();
        return (List<T>) deserialize(value, type);
    }

    public <T, Q> T unBox(Q treeMap, Type type) {
        String value = getJsonString(treeMap);
        return (T) deserialize(value, type);
    }

    public <T> T findFirst(List<T> dataList) {
        if(dataList.isEmpty()) return null;
        return dataList.stream().findFirst().orElse(null);
    }

    public <T> T findAny(List<T> dataList) {
        if(dataList.isEmpty()) return null;
        return dataList.stream().findAny().orElse(null);
    }

    public <T> List<T> findByPredicate(List<T> dataList, Predicate predicate) {
        if(dataList.isEmpty()) return null;
        return (List<T>) dataList.stream().filter(predicate).collect(Collectors.toList());
    }

    private <T> String getJsonString(T type) {
        return GsonParser.toInstance().serialize(type);
    }

    private <T> T deserialize(String value, Class<T> tClass) {
        return GsonParser.toInstance().deserialize(value, tClass);
    }

    private Object deserialize(String value, Type type) {
        return new Gson().fromJson(value, type); //TODO : Move this to json parser
    }
}
