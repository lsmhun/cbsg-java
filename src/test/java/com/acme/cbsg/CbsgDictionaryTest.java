package com.acme.cbsg;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Stream;

import static com.acme.cbsg.CbsgCore.VAR_PATTERN;
import static org.junit.jupiter.api.Assertions.*;

class CbsgDictionaryTest {


    @Test
    void getGrowthSuperlative() {
        CbsgDictionary cbsgDictionary = new CbsgDictionary(CbsgDictionary.DEFAULT_DICTIONARY_FILE);
        Map<String, Integer> superlatives = cbsgDictionary.sentenceWithWeight(CbsgDictionaryKey.WORD_GROWTH);
        assertFalse(superlatives.isEmpty());
    }

    @Test
    void dataNotFound() {
        CbsgDictionary cbsgDictionary = new CbsgDictionary();
        Map<String, Integer> sentenceWithWeight = cbsgDictionary.sentenceWithWeight("shalala");
        assertTrue(sentenceWithWeight.isEmpty());
    }

    @Test
    void senwOrg() {
        CbsgDictionary cbsgDictionary = new CbsgDictionary();
        Map<String, Integer> sentenceWithWeight = cbsgDictionary.sentenceWithWeight(CbsgDictionaryKey.SENW_ORG);
        assertFalse(sentenceWithWeight.isEmpty());
        assertTrue(sentenceWithWeight.containsKey("organization"));
        assertEquals(2, sentenceWithWeight.get("organization"));
    }

    @Test
    void senwThingInner() {
        CbsgDictionary cbsgDictionary = new CbsgDictionary();
        Map<String, Integer> sentenceWithWeight = cbsgDictionary.sentenceWithWeight(CbsgDictionaryKey.SENW_THING_INNER);
        assertFalse(sentenceWithWeight.isEmpty());
        assertTrue(sentenceWithWeight.containsKey("Quality Management System"));
        assertEquals(1, sentenceWithWeight.get("Quality Management System"));
    }

    @Test
    void collectAllVarTemplates() throws IOException, URISyntaxException {
        Set<String> variables = new HashSet<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        URL url = classloader.getResource("dict/en/");
        assert url != null;
        Path path = Paths.get(url.toURI());
        try (Stream<Path> entries = Files.walk(path, 5, FileVisitOption.FOLLOW_LINKS)
                .onClose(() -> System.out.println("The Stream is closed"))) {
            entries.forEach(p ->
                    {
                        Scanner scanner;
                        try {
                            scanner = new Scanner(p);
                            while (scanner.hasNext()) {
                                String nextToken = scanner.next();
                                if (nextToken.startsWith("$")) {
                                    Matcher matcher = VAR_PATTERN.matcher(nextToken);
                                    while (matcher.find()) {
                                        variables.add(matcher.group());
                                    }
                                }
                            }
                        } catch (IOException e) {
                            // do nothing
                        }
                    }
            );
            assertFalse(variables.isEmpty());
            variables.stream().sorted().forEach(System.out::println);
        }
    }
}