package com.acme.cbsg;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class CbsgResourceUtil {

    public static final String DEFAULT_DICTIONARY_PROPERIES = "dict/en/dictionary.properties";

    private final Map<String, List<String>> listCache = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Integer>> sentenceCache = new ConcurrentHashMap<>();

    public List<String> stringList(final String resourceFileName){
        if(resourceFileName == null || "".equals(resourceFileName)){
            return new ArrayList<>();
        }
        return listCache.computeIfAbsent(resourceFileName, s -> {
            Scanner scanner = getScannerForResourceFile(s);
            List<String> res = new ArrayList<>();
            while (scanner.hasNext()){
                res.add(scanner.nextLine());
            }
            return res;
        });
    }

    public Map<String, Integer> sentenceWithWeight(final String resourceFileName){
        if(resourceFileName == null || "".equals(resourceFileName)){
            return new HashMap<>();
        }
        return sentenceCache.computeIfAbsent(resourceFileName, s -> {
            Scanner scanner = getScannerForResourceFile(s);
            Map<String, Integer> res = new HashMap<>();
            while (scanner.hasNext()) {
                try {
                    String line = scanner.nextLine();
                    String[] l = line.split(",", 2);
                    if (l.length != 2) {
                        continue;
                    }
                    Integer weight = Integer.valueOf(l[0]);
                    res.put(l[1], weight);
                } catch (Exception ex){
                    // sad, this won't be part of the sentences, probably format error
                }
            }
            return res;
        });
    }

    private Scanner getScannerForResourceFile(String resourceFileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(resourceFileName);
        //todo: enable this null check
        Scanner scanner;
        if(is == null){
            scanner = new Scanner("");
        } else {
         scanner = new Scanner(is);
        }
        return scanner;
    }

    public Properties readProperties(String resourcePropertiesLocation){
        InputStream inputStream;
        Properties properties = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        inputStream = classloader.getResourceAsStream(resourcePropertiesLocation);
        if(inputStream == null){
            inputStream = classloader.getResourceAsStream(DEFAULT_DICTIONARY_PROPERIES);
        }
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
