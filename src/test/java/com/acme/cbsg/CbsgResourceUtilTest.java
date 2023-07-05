package com.acme.cbsg;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CbsgResourceUtilTest {

    private final CbsgResourceUtil cbsgResourceUtil = new CbsgResourceUtil();

    @Test
    void getGrowthSuperlative() {
        List<String> superlatives = cbsgResourceUtil.stringList(Constant.WORD_GROWTH_NOT_PLURAL);
        assertFalse(superlatives.isEmpty());
    }

    //@Test
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

}