package com.acme.cbsg;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class CbsgDictionary {

    public static final String DEFAULT_DICTIONARY_FILE = "dict/en/cbsg_db.csv";

    public CbsgDictionary() {
        loadDictionary(DEFAULT_DICTIONARY_FILE);
    }

    public CbsgDictionary(String dictionaryFile) {
        loadDictionary(dictionaryFile);
    }

    private final Map<String, Map<String, Integer>> sentenceCache = new ConcurrentHashMap<>();

    public Map<String, Integer> sentenceWithWeight(final String resourceKey){
        if(resourceKey == null || "".equals(resourceKey) || !sentenceCache.containsKey(resourceKey)){
            return new HashMap<>();
        }
        return sentenceCache.getOrDefault(resourceKey, new HashMap<>());
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

    private void loadDictionary(String dictionaryFile){
        Scanner scanner = getScannerForResourceFile(dictionaryFile);
        while (scanner.hasNext()) {
            try {
                String line = scanner.nextLine();
                String[] l = line.split(",", 3);
                if (l.length != 3) {
                    continue;
                }
                String key = l[0];
                Integer weight = Integer.valueOf(l[1]);
                if (!sentenceCache.containsKey(key)) {
                    sentenceCache.put(key, new HashMap<>());
                }
                sentenceCache.get(key).put(l[2], weight);
            } catch (Exception ex){
                // sad, this won't be part of the sentences, probably format error
            }
        }
    }
}
