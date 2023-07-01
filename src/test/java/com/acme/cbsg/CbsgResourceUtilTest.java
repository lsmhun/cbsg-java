package com.acme.cbsg;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CbsgResourceUtilTest {

    private CbsgResourceUtil cbsgResourceUtil = new CbsgResourceUtil();

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
    void sout(){
        List<String> a = List.of(
                "manage", "target", "streamline", "improve", "optimize", "achieve",
                "secure", "address", "boost", "deploy", "innovate", "right-scale",
                "formulate", "transition", "leverage", "focus on", "synergize",
                "generate", "analyse", "integrate", "empower", "benchmark", "learn",
                "adapt", "enable", "strategize", "prioritize", "pre-prepare",
                "deliver", "champion", "embrace", "enhance", "engineer", "envision",
                "incentivize", "maximize", "visualize", "whiteboard",
                "institutionalize", "promote", "overdeliver", "right-size",
                "rebalance", "re-imagine", "influence", "facilitate", "drive",
                "structure", "standardize", "accelerate", "deepen", "strengthen",
                "broaden", "enforce", "establish", "foster", "build", "differentiate",
                "take a bite out of", "table", "flesh out", "reach out", "jump-start",
                "co-create", "capitalize on", "calibrate", "re-aggregate",
                "articulate", "iterate", "reinvest in", "potentiate", "front-face",
                "co-develop", "take control of", "robustify", "harness", "activate",
                "showcase", "cherry-pick", "digitize", "re-invent", "springboard",
                "solutionize", "re-content", "commoditize", "be eager for",
                "productize", "repurpose", "reenergize", "co-specify", "codify",
                "cross-pollinate", "ignite", "transgenerate", "orchestrate",
                "envisioneer", "reintermediate", "reframe", "control", "ideate",
                "reprioritize", "operate", "cascade"
        );
        for(String s: a){
            System.out.println(s);
        }
    }
}