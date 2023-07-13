package com.acme.cbsg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acme.cbsg.Constant.*;

public class CbsgMain {

    private final static CbsgResourceUtil dict = new CbsgResourceUtil();

    final static String VAR_REGEXP = "\\$[a-zA-Z0-9]+";
    final static Pattern VAR_PATTERN = Pattern.compile(VAR_REGEXP);
    public static Random rand = new Random();

    public static void main(String... args) {
        // Corporate Bullshit Generator
        System.out.println(workshop());
    }

    ///////////////// PUBLIC        ///////////////////

    public static String sentenceGuaranteedAmount(int count) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < count; i++) {
            ret.append(sentence()).append(" ");
        }
        return cleanup(ret.toString());
    }

    public static String workshop() {
        return sentenceGuaranteedAmount(500);
    }

    public static String shortWorkshop() {
        return sentenceGuaranteedAmount(5);
    }

    public static String financialReport() {
        return sentences();
    }

    ///////////////// TEXT ELEMENTS ///////////////////

    public static String sentence() {
        String propositions = articulatedPropositions();
        return propositions.substring(0, 1).toUpperCase() + propositions.substring(1) + ".";
    }

    public static String sentences() {
        StringBuilder ret = new StringBuilder();
        int pm = rand.nextInt(10);
        int limit = (rand.nextBoolean()) ? 30 + pm : 30 - pm;
        int cnt = 0;
        // average 30 sentence +-10
        while (limit != cnt) {
            ret.append(sentence()).append(" ");
            cnt++;
        }
        return cleanup(ret.toString());
    }

    public static String addIndefiniteArticle(String word, boolean plural) {
        if (plural || word.length() == 0) {
            return word;
        }

        if (word.charAt(0) == 'a' || word.charAt(0) == 'e' || word.charAt(0) == 'i' || word.charAt(0) == 'o' || word.charAt(0) == 'u') {
            return "an " + word;
        }

        return "a " + word;
    }

    public static String sillyAbbreviationGeneratorSAS(String s) {
        String[] words = s.split(" ");
        StringBuilder abbreviation = new StringBuilder();

        for (String word : words) {
            abbreviation.append(word.charAt(0));
        }

        return abbreviation.toString();
    }

    public static String boss() {
        String department = randomChoice(dict.stringList(WORD_BOSS_DEPARTMENT));
        String departmentOrTopRole = weightedChoice(dict.sentenceWithWeight(SENW_BOSS_DEPT));

        if (rand.nextInt(4) == 1) {
            String managing = weightedChoice(dict.sentenceWithWeight(SENW_BOSS_MANAGING));
            String vice = weightedChoice(dict.sentenceWithWeight(SENW_BOSS_VICE));
            String co = weightedChoice(dict.sentenceWithWeight(SENW_BOSS_CO));
            String title = String.format(weightedChoice(dict.sentenceWithWeight(SENW_BOSS_TITLE)), co, vice);

            String age = weightedChoice(dict.sentenceWithWeight(SENW_BOSS_AGE));
            String executive = weightedChoice(dict.sentenceWithWeight(SENW_BOSS_EXECUTIVE));
            return managing + age + executive + title + " of " + department;
        }
        String groupal = weightedChoice(dict.sentenceWithWeight(SENW_BOSS_GROUPAL));
        String officerOrCatalyst = weightedChoice(dict.sentenceWithWeight(SENW_BOSS_OFFICER));
        return groupal + abbreviate("Chief " + departmentOrTopRole + " " +
                officerOrCatalyst, 0.6);
    }

    public static String person() {
        String personTemplate = weightedChoice(dict.sentenceWithWeight(SENW_PERSON));
        if(personTemplate == null || "".equals(personTemplate)){
            return randomChoice(dict.stringList(WORD_PERSON));
        }
        return evaluateValues(personTemplate);
    }

    public static String personPlural() {
        return randomChoice(dict.stringList(WORD_PERSON_PLURAL));
    }


    public static String timelessEvent() {
        return randomChoice(dict.stringList(WORD_TIMELESS_EVENT));
    }

    public static String growthAtom() {
        return randomChoice(dict.stringList(WORD_GROWTH_ATOM_NOT_PLURAL));
    }

    public static String growth() {
        String superlative = randomChoice(dict.stringList(WORD_GROWTH_NOT_PLURAL));
        return superlative + " " + growthAtom();
    }

    public static String thingInner() {
        String weightedThingInner = weightedChoice(dict.sentenceWithWeight(SENW_THING_INNER));
        if(weightedThingInner == null || "".equals(weightedThingInner)){
            return randomChoice(dict.stringList(WORD_THING_INNER));
        }
        String res = evaluateValues(weightedThingInner);
        Map<String, Integer> senwOrg = dict.sentenceWithWeight(SENW_ORG);
        for(String org: senwOrg.keySet()){
            if(res.contains(org)){
                return res;
            }
        }
        return abbreviate(res, 0.5);
    }

    private static String matrixOrSO() {
        return weightedChoice(dict.sentenceWithWeight(SENW_ORG));
    }

    public static String thingAtom() {
        String weightedThingAtom = weightedChoice(dict.sentenceWithWeight(SENW_THING_ATOM));
        if(weightedThingAtom == null || "".equals(weightedThingAtom)){
            return randomChoice(dict.stringList(WORD_THING_ATOM));
        }
        if(weightedThingAtom.contains("$")){
            return evaluateValues(weightedThingAtom);
        }
        return abbreviate(weightedThingAtom, 0.5);
    }

    public static String thingAtomPlural() {
        String weightedThingAtom = weightedChoice(dict.sentenceWithWeight(SENW_THING_ATOM_PLURAL));
        if(weightedThingAtom == null || "".equals(weightedThingAtom)){
            return randomChoice(dict.stringList(WORD_THING_ATOM_PLURAL));
        }
        return makeEventualPlural(evaluateValues(weightedThingAtom), true);
    }


    public static String badThings() {
        return randomChoice(dict.stringList(WORD_BAD_THINGS));
    }

    public static String thingAdjective() {
        return randomChoice(dict.stringList(WORD_THING_ADJECTIVE));
    }

    public static String eventualAdverb() {
        if (rand.nextInt(4) == 1) {
            return randomChoice(dict.stringList(WORD_ADVERB_EVENTUAL));
        }
        return "";
    }

    public static String thing() {
        String weightedThing = weightedChoice(dict.sentenceWithWeight(SENW_THING));
        return evaluateValues(weightedThing);
    }

    public static String thingPlural() {
        String weightedThing = weightedChoice(dict.sentenceWithWeight(SENW_THING_PLURAL));
        return evaluateValues(weightedThing);
    }

    public static String addRandomArticle(String word, boolean plural) {
        int r = rand.nextInt(15);
        if (r <= 2) {
            return "the " + word;
        } else if (r < 6) {
            return "our " + word;
        }
        return addIndefiniteArticle(word, plural);
    }

    public static String thingWithRandomArticle(){
        if (rand.nextInt(100) == 1) {
            return "the 'why' behind " + thing();
        }
        return addRandomArticle(thing(), false);
    }

    public static String thingWithRandomArticlePlural() {
        return addRandomArticle(thingPlural(), true);
    }

    public static String innerPersonVerbHavingThingComplement() {
        return randomChoice(dict.stringList(WORD_PERSON_INNER_HAVING_THING_COMPLEMENT));
    }

    public static String personVerbHavingThingComplement(boolean plural, boolean infinite) {
        if (infinite) {
            innerPersonVerbHavingThingComplement();
        }
        return buildPluralVerb(innerPersonVerbHavingThingComplement(), plural);
    }

    public static String personVerbHavingBadThingComplement(boolean plural) {
        String inner = randomChoice(dict.stringList(WORD_PERSON_HAVING_BAD_THING_COMPLEMENT));
        return buildPluralVerb(inner, plural);
    }

    public static String personVerbHavingPersonComplement(boolean plural) {
        String inner = randomChoice(dict.stringList(WORD_PERSON_HAVING_PERSON_COMPLEMENT));
        return buildPluralVerb(inner, plural);
    }

    public static String thingVerbAndDefiniteEnding(boolean plural) {
        String inner = randomChoice(dict.stringList(WORD_THING_VERB_DEFINITE_ENDING));
        return buildPluralVerb(inner, plural);
    }

    public static String thingVerbHavingThingComplement(boolean plural) {
        String inner = randomChoice(dict.stringList(WORD_THING_VERB_HAVING_COMPLEMENT));
        return buildPluralVerb(inner, plural);
    }

    public static String thingVerbAndEnding() {
        boolean compl_sp = rand.nextBoolean();
        int r = rand.nextInt(103);
        if (r < 55) {
            return thingVerbHavingThingComplement(false) + " " + thingWithRandomArticle();
                    //thingWithRandomArticle(compl_sp));
        } else if (r < 100) {
            return personVerbHavingPersonComplement(false) + " the " +
                    (compl_sp? person() : personPlural());
        }
        return thingVerbAndDefiniteEnding(false);
    }

    public static String thingVerbAndEndingPlural() {
        boolean compl_sp = rand.nextBoolean();
        int r = rand.nextInt(103);
        if (r < 55) {
            return thingVerbHavingThingComplement(true) + " " + thingWithRandomArticlePlural();
                    //thingWithRandomArticle(compl_sp));
        } else if (r < 100) {
            return personVerbHavingPersonComplement(true) + " the " +
                    ( compl_sp? person() : personPlural());
        }
        return thingVerbAndDefiniteEnding(true);
    }

    public static String personVerbAndEnding(boolean plural, boolean infinitive) {
        boolean compl_sp = rand.nextBoolean();
        int r = rand.nextInt(95);
        if (r < 10) {
            return personVerbAndDefiniteEnding(plural, infinitive);
        } else if (r < 15) {
            return (personVerbHavingBadThingComplement(plural) + " " +
                    addRandomArticle(badThings(), plural));
        }
        return personVerbHavingThingComplement(plural, infinitive) + " " +
                (compl_sp? thingWithRandomArticle() : thingWithRandomArticlePlural() );
    }

    public static String personInfinitiveVerbAndEnding() {
        return personVerbAndEnding(true, true);
    }

    public static String faukon() {
        String weightedProposition = weightedChoice(dict.sentenceWithWeight(SENW_FAUKON));
        if(weightedProposition == null || "".equals(weightedProposition)){
            return randomChoice(dict.stringList(WORD_FAUKON));
        }
        return evaluateValues(weightedProposition);
    }

    public static String eventualPostfixedAdverb() {
        String weightedProposition = weightedChoice(dict.sentenceWithWeight(SENW_EVENTUAL_POSTFIXED_ADVERB));
        if(weightedProposition == null || "".equals(weightedProposition)){
            return randomChoice(dict.stringList(WORD_ADVERB_EVENTUAL_POSTFIXED));
        }
        return evaluateValues(weightedProposition);
    }

    public static String personVerbAndDefiniteEnding(boolean plural, boolean infinitive) {
        String weightedProposition = weightedChoice(dict.sentenceWithWeight(SENW_PERSON_VERB_AND_DEFINITE_ENDING));
        String inner=(weightedProposition == null || "".equals(weightedProposition))
                ? randomChoice(dict.stringList(WORD_PERSON_VERB_DEFINITE_ENDING))
                : evaluateValues(weightedProposition);
        if (infinitive) {
            return inner;
        }
        return buildPluralVerb(inner, plural);
    }

    public static String proposition() {
        String weightedProposition = weightedChoice(dict.sentenceWithWeight(SENW_PROPOSITION));
        return evaluateValues(weightedProposition);
    }

    public static String articulatedPropositions() {
        String weightedProposition = weightedChoice(dict.sentenceWithWeight(SENW_ARTICULATED_PROPOSITION));
        return evaluateValues(weightedProposition);
    }

    static String evaluateValues(final String template) {
        List<String> values = new ArrayList<>();
        Matcher matcher = VAR_PATTERN.matcher(template);
        while (matcher.find()){
            // ie: it will evaluate $thingAtom to it values
            values.add(templateFunction(matcher.group()));
        }
        String templateReplace = template.replaceAll(VAR_REGEXP, "%s");
        return String.format(templateReplace, values.toArray());
    }

    public static String templateFunction(final String templateName){
        String result = "";
        switch (templateName){
            case "$faukon" : result = faukon();break;
            case "$sentence" : result = sentence();break;
            case "$thing" : result = thing();break;
            case "$thingPlural" : result = thingPlural();break;
            case "$thingRandom" : result = rand.nextBoolean() ? thing() : thingPlural();break;
            case "$thingInner" : result = thingInner();break;
            case "$thingAdjective" : result = thingAdjective();break;
            case "$thingAtom" : result = thingAtom();break;
            case "$thingAtomRandom" : result = rand.nextBoolean() ? thingAtom() : thingAtomPlural();break;
            case "$thingAtomPlural" : result = thingAtomPlural();break;
            case "$thingVerbAndEnding" : result = thingVerbAndEnding();break;
            case "$thingVerbAndEndingPlural" : result = thingVerbAndEndingPlural();break;
            case "$thingWithRandomArticle" : result = thingWithRandomArticle();break;
            case "$thingWithRandomArticlePlural" : result = thingWithRandomArticlePlural();break;
            case "$thingWithRandomArticleRandom" : result = rand.nextBoolean() ? thingWithRandomArticle() : thingWithRandomArticlePlural();break;
            case "$person" : result = person();break;
            case "$personPlural" : result = personPlural();break;
            case "$personRandom" : result = rand.nextBoolean() ? person() : personPlural();break;
            case "$personInfinitiveVerbAndEnding" : result = personInfinitiveVerbAndEnding();break;
            case "$personVerbAndEnding" : result = personVerbAndEnding(false, false);break;
            case "$boss" : result = boss();break;
            case "$eventualAdverb" : result = eventualAdverb();break;
            case "$eventualPostfixedAdverb" : result = eventualPostfixedAdverb();break;
            case "$growthAtom" : result = growthAtom();break;
            case "$addIndefiniteArticleGrowth" : result = addIndefiniteArticle(growth(), false);break;
            case "$addIndefiniteArticleGrowthPlural" : result = addIndefiniteArticle(growth(), true);break;
            case "$addIndefiniteArticleThing" : result = addIndefiniteArticle(thing(), false);break;
            case "$addIndefiniteArticleThingPlural" : result = addIndefiniteArticle(thingPlural(), true);break;
            case "$addIndefiniteArticleThingRandom" : result = rand.nextBoolean() ? addIndefiniteArticle(thing(), false) : addIndefiniteArticle(thingPlural(), true);break;
            case "$proposition" : result = proposition();break;
            case "$matrixOrSOPlural" : result = makeEventualPlural(matrixOrSO(), true);break;
            case "$matrixOrSO" : result = matrixOrSO();break;
            case "$timelessEvent": result = timelessEvent();break;

        }
        return result;
    }

    ///////////////// UTILITIES     ///////////////////
    public static String randomChoice(List<String> words) {
        return words.get(rand.nextInt(words.size()));
    }

    public static String makeEventualPlural(String word, boolean plural) {
        if (word.length() < 3 || !plural) {
            return word;
        }

        try {
            int abbr = word.indexOf(" (");
            return makeEventualPlural(word.substring(0, abbr), plural) + word.substring(abbr);
        } catch (StringIndexOutOfBoundsException e) {
            // Do nothing
        }

        if (word.equals("matrix")) {
            return "matrices";
        } else if (word.equals("analysis")) {
            return "analyses";
        } else if (word.endsWith("gh")) {
            return word + "s";
        } else if (word.endsWith("s") || word.endsWith("x") || word.endsWith("z") || word.endsWith("h")) {
            return word + "es";
        } else if (word.endsWith("y") && !word.toLowerCase().endsWith("ay") && !word.toLowerCase().endsWith("ey") && !word.toLowerCase().endsWith("iy") && !word.toLowerCase().endsWith("oy") && !word.toLowerCase().endsWith("uy")) {
            return word.substring(0, word.length() - 1) + "ies";
        }

        return word + "s";
    }

    public static String buildPluralVerb(String verb, boolean plural) {
        int last = verb.trim().length() - 1;
        if (last < 0) {
            return verb;
        }

        if (plural) {
            return verb;
        }

        String sExtension = verb.substring(0, last + 1) + "s" + verb.substring(last + 1);
        String esExtension = verb.substring(0, last + 1) + "es" + verb.substring(last + 1);
        if (verb.charAt(last) == 's' || verb.charAt(last) == 'o' || verb.charAt(last) == 'z') {
            return esExtension;
        } else if (verb.charAt(last) == 'h') {
            if (verb.charAt(last - 1) == 'c' || verb.charAt(last - 1) == 's') {
                return esExtension;
            }
            return sExtension;
        } else if (verb.charAt(last) == 'y') {
            if (verb.charAt(last - 1) == 'a' || verb.charAt(last - 1) == 'e' || verb.charAt(last - 1) == 'i' || verb.charAt(last - 1) == 'o' || verb.charAt(last - 1) == 'u') {
                return sExtension;
            }
            return verb.substring(0, last) + "ies" + verb.substring(last + 1);
        }

        return sExtension;
    }

    public static String abbreviate(String s, double probability) {
        if (Math.random() < probability) {
            return s + " (" + sillyAbbreviationGeneratorSAS(s) + ")";
        }
        return s;
    }


    public static String weightedChoice(Map<String, Integer> choices) {
        int totalWeight = choices.values().stream().mapToInt(Integer::intValue).sum();

        int randomWeight = rand.nextInt(totalWeight);
        for (Map.Entry<String, Integer> entry : choices.entrySet()) {
            randomWeight -= entry.getValue();
            if (randomWeight < 0) {
                return entry.getKey();
            }
        }

        return "";
    }

    public static String cleanup(String text) {
        return text
                .replaceAll("\\s+", " ")
                .replaceAll(";\\.",".")
                .replaceAll(" ,", ",");
    }

}