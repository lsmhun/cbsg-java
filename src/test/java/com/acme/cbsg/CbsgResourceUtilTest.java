package com.acme.cbsg;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Stream;

import static com.acme.cbsg.CbsgMain.VAR_PATTERN;
import static org.junit.jupiter.api.Assertions.*;

class CbsgResourceUtilTest {

    private final CbsgResourceUtil cbsgResourceUtil = new CbsgResourceUtil();

    @Test
    void getGrowthSuperlative() {
        List<String> superlatives = cbsgResourceUtil.stringList(Constant.WORD_GROWTH_NOT_PLURAL);
        assertFalse(superlatives.isEmpty());
    }

    @Test
    void dataNotFound() {
        List<String> words = cbsgResourceUtil.stringList("shalala");
        assertTrue(words.isEmpty());
    }

    @Test
    void senwOrg() {
        Map<String, Integer> sentenceWithWeight = cbsgResourceUtil.sentenceWithWeight(Constant.SENW_ORG);
        assertFalse(sentenceWithWeight.isEmpty());
        assertTrue(sentenceWithWeight.containsKey("organization"));
        assertEquals(2, sentenceWithWeight.get("organization"));
    }

    @Test
    void senwThingInner() {
        Map<String, Integer> sentenceWithWeight = cbsgResourceUtil.sentenceWithWeight(Constant.SENW_THING_INNER);
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
        //Files.walk(path, 5).forEach(p -> System.out.printf("- %s%n", p.toString()));
        Files.walk(path, 5).forEach(p ->
                {
                    Scanner scanner;
                    try {
                        scanner = new Scanner(p);
                        while (scanner.hasNext()) {
                            String nextToken = scanner.next();
                            if (nextToken.startsWith("$")) {
                                Matcher matcher = VAR_PATTERN.matcher(nextToken);
                                while (matcher.find()){
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