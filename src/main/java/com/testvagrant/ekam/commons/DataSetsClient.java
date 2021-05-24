package com.testvagrant.ekam.commons;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.testvagrant.ekam.commons.cache.DataSetsCache;
import com.testvagrant.ekam.commons.exceptions.NoSuchKeyException;
import com.testvagrant.optimus.commons.filehandlers.GsonParser;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class DataSetsClient {

    private DataSetsCache dataSetsCache;

    @Inject
    public DataSetsClient(DataSetsCache dataSetsCache) {
        this.dataSetsCache = dataSetsCache;
    }

    public <T> T getValue(String key, Class<T> tClass) throws NoSuchKeyException {
        return getValue(key, tClass, false);
    }

    public <T> T getValue(String key, Class<T> tClass, boolean lock) throws NoSuchKeyException {
        String value = getJsonString(key, lock);
        T unboxedValue = deserialize(value, tClass);
        return unboxedValue;
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

    protected boolean isAFile(String key) {
        return key.contains(".json");
    }

    public void release(String key, Predicate<Map.Entry<String, LinkedTreeMap>> entryPredicate) {
        dataSetsCache.release(key, entryPredicate);
    }

    public void release(Predicate<Map.Entry<String, LinkedTreeMap>> entryPredicate) {
        dataSetsCache.release(entryPredicate);
    }

    private <T> String getJsonString(T type) {
        return GsonParser.toInstance().serialize(type);
    }

    private <T> String getJsonString(String key) throws NoSuchKeyException {
        return getJsonString(key, false);
    }

    private <T> String getJsonString(String key, boolean lock) throws NoSuchKeyException {
        T type = (T) dataSetsCache.get(key, lock);
        return GsonParser.toInstance().serialize(type);
    }

    private <T> T deserialize(String value, Class<T> tClass) {
        return GsonParser.toInstance().deserialize(value, tClass);
    }

    private Object deserialize(String value, Type type) {
        return new Gson().fromJson(value, type); //TODO : Move this to json parser
    }

}
