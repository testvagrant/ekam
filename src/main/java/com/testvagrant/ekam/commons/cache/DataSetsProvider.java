package com.testvagrant.ekam.commons.cache;


import com.google.gson.internal.LinkedTreeMap;
import com.google.inject.Provider;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.cache.DataSetsCache;
import com.testvagrant.optimus.commons.filehandlers.FileFinder;
import com.testvagrant.optimus.commons.filehandlers.GsonParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataSetsProvider implements Provider<DataSetsCache> {

    public DataSetsCache load() {
        String dataSetsRootPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("dataSets")).getPath();
        String envPath = Paths.get(dataSetsRootPath, SystemProperties.ENV).toString();
        List<File> files = FileFinder.fileFinder(envPath).find( ".json");
        DataSetsCache dataSetsCache = new DataSetsCache();
        files.forEach(file -> {
            try {
                Map<String, LinkedTreeMap> dataSetMap = GsonParser.toInstance().deserialize(new FileReader(file), Map.class);
                dataSetMap.entrySet().parallelStream().forEach((entry) -> {
                    dataSetsCache.put(entry.getKey(), entry.getValue());
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return dataSetsCache;
    }

    @Override
    public DataSetsCache get() {
        return load();
    }
}
