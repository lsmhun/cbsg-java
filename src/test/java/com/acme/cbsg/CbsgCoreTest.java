package com.acme.cbsg;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
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

class CbsgCoreTest {

    private final static CbsgResourceUtil cbsgResourceUtil = new CbsgResourceUtil();

    @Test
    void sentence() {
        Cbsg cbsgTest = new CbsgCore();
        String result = cbsgTest.sentenceGuaranteedAmount(1);
        System.out.println(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shortWorkshopWithAaaaaaa() {
        Properties properties = cbsgResourceUtil.readProperties("dict/test/test_dictionary.properties");
        Cbsg cbsgTest = new CbsgCore(properties);
        String result = cbsgTest.shortWorkshop();
        assertFalse(result.isEmpty());
        String[] splitted = result.split(" ");
        assertNotEquals(0, splitted.length);
        Set<String> mySet = new HashSet<>(Arrays.asList(splitted));
        assertEquals(1, mySet.size());
    }

    @Test
    void shortWorkshopWithEmptyDict() {
        Properties properties = new Properties();
        Cbsg cbsgTest = new CbsgCore(properties);
        String result = cbsgTest.shortWorkshop();
        assertTrue(result.trim().isEmpty());
    }

    @Test
    void callAllTemplatesChaosTesting() throws IOException, URISyntaxException {
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
        }
        assertFalse(variables.isEmpty());
        Map<String, String> templateResult = new HashMap<>();
        CbsgCore cbsg = new CbsgCore();
        for (int i = 0; i < 10; i++) {
            for (String templateVar : variables) {
                templateResult.put(templateVar, cbsg.templateFunction(templateVar));
            }
        }
        assertEquals(variables.size(), templateResult.size());
    }
}