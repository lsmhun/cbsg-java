package com.acme.cbsg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acme.cbsg.CbsgDictionaryKey.*;

public final class CbsgCore implements Cbsg {

    private final CbsgDictionary dict;
//    private final CbsgDictionary cbsgConfiguration;
//    private final Properties p;

    final static String VAR_REGEXP = "\\$[a-zA-Z\\d]+";
    final static Pattern VAR_PATTERN = Pattern.compile(VAR_REGEXP);
    public static Random rand = new Random();

    public CbsgCore() {
        this.dict = new CbsgDictionary();
//        this.p = dict.readProperties(CbsgResourceUtil.DEFAULT_DICTIONARY_PROPERIES);
//        this.cbsgConfiguration = dictionaryUtil.readProperties()
    }

//    public CbsgCore(Properties cbsgConfiguration) {
//        this.p = cbsgConfiguration;
//    }

    public CbsgCore(CbsgDictionary dictionary) {
        dict = dictionary;
    }


    ///////////////// PUBLIC        ///////////////////

    public String sentenceGuaranteedAmount(int count) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < count; i++) {
            ret.append(sentence()).append(" ");
        }
        return cleanup(ret.toString());
    }

    public String workshop() {
        return sentenceGuaranteedAmount(500);
    }

    public String shortWorkshop() {
        return sentenceGuaranteedAmount(5);
    }

    public String financialReport() {
        return sentences();
    }

    ///////////////// TEXT ELEMENTS ///////////////////

    private String sentence() {
        String propositions = articulatedPropositions();
        if(propositions.isEmpty()){
            return propositions;
        }
        return propositions.substring(0, 1).toUpperCase() + propositions.substring(1) + ".";
    }

    private String sentences() {
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

    private String addIndefiniteArticle(String word, boolean plural) {
        if (plural || word.length() == 0) {
            return word;
        }

        if (word.charAt(0) == 'a' || word.charAt(0) == 'e' || word.charAt(0) == 'i' || word.charAt(0) == 'o' || word.charAt(0) == 'u') {
            return "an " + word;
        }

        return "a " + word;
    }

    private String sillyAbbreviationGeneratorSAS(String s) {
        String[] words = s.split(" ");
        StringBuilder abbreviation = new StringBuilder();

        for (String word : words) {
            abbreviation.append(word.charAt(0));
        }

        return abbreviation.toString();
    }

    private String boss() {
        String department = weightedChoice(WORD_BOSS_DEPARTMENT);
        String departmentOrTopRole = weightedChoice(SENW_BOSS_DEPT);

        if (rand.nextInt(4) == 1) {
            String managing = weightedChoice(SENW_BOSS_MANAGING);
            String vice = weightedChoice(SENW_BOSS_VICE);
            String co = weightedChoice(SENW_BOSS_CO);
            String title = String.format(weightedChoice(SENW_BOSS_TITLE), co, vice);

            String age = weightedChoice(SENW_BOSS_AGE);
            String executive = weightedChoice(SENW_BOSS_EXECUTIVE);
            return managing + age + executive + title + " of " + department;
        }
        String groupal = weightedChoice(SENW_BOSS_GROUPAL);
        String officerOrCatalyst = weightedChoice(SENW_BOSS_OFFICER);
        return groupal + abbreviate("Chief " + departmentOrTopRole + " " +
                officerOrCatalyst, 0.6);
    }

    private String person() {
        String personTemplate = weightedChoice(SENW_PERSON);
        if(personTemplate == null || "".equals(personTemplate)){
            return weightedChoice(WORD_PERSON);
        }
        return evaluateValues(personTemplate);
    }

    private String personPlural() {
        return weightedChoice(WORD_PERSON_PLURAL);
    }


    private String timelessEvent() {
        return weightedChoice(WORD_TIMELESS_EVENT);
    }

    private String growthAtom() {
        return weightedChoice(WORD_GROWTH_ATOM);
    }

    private String growth() {
        String superlative = weightedChoice(WORD_GROWTH);
        return superlative + " " + growthAtom();
    }

    private String thingInner() {
        String weightedThingInner = weightedChoice(SENW_THING_INNER);
        if(weightedThingInner == null || "".equals(weightedThingInner)){
            return weightedChoice(WORD_THING_INNER);
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

    private String matrixOrSO() {
        return weightedChoice(SENW_ORG);
    }

    private String thingAtom() {
        String weightedThingAtom = weightedChoice(SENW_THING_ATOM);
        if(weightedThingAtom == null || "".equals(weightedThingAtom)){
            return weightedChoice(WORD_THING_ATOM);
        }
        if(weightedThingAtom.contains("$")){
            return evaluateValues(weightedThingAtom);
        }
        return abbreviate(weightedThingAtom, 0.5);
    }

    private String thingAtomPlural() {
        String weightedThingAtom = weightedChoice(SENW_THING_ATOM_PLURAL);
        if(weightedThingAtom == null || "".equals(weightedThingAtom)){
            return weightedChoice(WORD_THING_ATOM_PLURAL);
        }
        return makeEventualPlural(evaluateValues(weightedThingAtom), true);
    }


    private String badThings() {
        return weightedChoice(WORD_BAD_THINGS);
    }

    private String thingAdjective() {
        return weightedChoice(WORD_THING_ADJECTIVE);
    }

    private String eventualAdverb() {
        if (rand.nextInt(4) == 1) {
            return weightedChoice(WORD_ADVERB_EVENTUAL);
        }
        return "";
    }

    private String thing() {
        String weightedThing = weightedChoice(SENW_THING);
        return evaluateValues(weightedThing);
    }

    private String thingPlural() {
        String weightedThing = weightedChoice(SENW_THING_PLURAL);
        return evaluateValues(weightedThing);
    }

    private String addRandomArticle(String word, boolean plural) {
        String weightedRandomArticle = weightedChoice(SENW_ADD_RANDOM_ARTICLE);
        if(weightedRandomArticle == null || "".equals(weightedRandomArticle)){
            return addIndefiniteArticle(word, plural);
        }
        return evaluateValues(String.format(weightedRandomArticle, word));
    }

    private String innerPersonVerbHavingThingComplement() {
        return weightedChoice(WORD_PERSON_INNER_HAVING_THING_COMPLEMENT);
    }

    private String personVerbHavingThingComplement(boolean plural, boolean infinite) {
        if (infinite) {
            innerPersonVerbHavingThingComplement();
        }
        return buildPluralVerb(innerPersonVerbHavingThingComplement(), plural);
    }

    private String personVerbHavingBadThingComplement(boolean plural) {
        String inner = weightedChoice(WORD_PERSON_HAVING_BAD_THING_COMPLEMENT);
        return buildPluralVerb(inner, plural);
    }

    private String personVerbHavingPersonComplement(boolean plural) {
        String inner = weightedChoice(WORD_PERSON_HAVING_PERSON_COMPLEMENT);
        return buildPluralVerb(inner, plural);
    }

    private String thingVerbAndDefiniteEnding(boolean plural) {
        String inner = weightedChoice(WORD_THING_VERB_DEFINITE_ENDING);
        return buildPluralVerb(inner, plural);
    }

    private String thingVerbHavingThingComplement(boolean plural) {
        String inner = weightedChoice(WORD_THING_VERB_HAVING_COMPLEMENT);
        return buildPluralVerb(inner, plural);
    }

    private String thingVerbAndEnding() {
        String weightedVerbAndEnding = weightedChoice(SENW_THING_VERB_ENDING);
        if(weightedVerbAndEnding == null || "".equals(weightedVerbAndEnding)){
            return thingVerbAndDefiniteEnding(false);
        }
        return evaluateValues(weightedVerbAndEnding);
    }

    private String thingVerbAndEndingPlural() {
        String weightedVerbAndEnding = weightedChoice(SENW_THING_VERB_ENDING_PLURAL);
        if(weightedVerbAndEnding == null || "".equals(weightedVerbAndEnding)){
            return thingVerbAndDefiniteEnding(true);
        }
        return evaluateValues(weightedVerbAndEnding);
    }

    private String personVerbAndEnding(boolean plural, boolean infinitive) {
        //todo
        boolean compl_sp = rand.nextBoolean();
        int r = rand.nextInt(95);
        if (r < 10) {
            return personVerbAndDefiniteEnding(plural, infinitive);
        } else if (r < 15) {
            return (personVerbHavingBadThingComplement(plural) + " " +
                    addRandomArticle(badThings(), plural));
        }
        return personVerbHavingThingComplement(plural, infinitive) + " " +
                (compl_sp? addRandomArticle(thing(), false) : addRandomArticle(thingPlural(), true) );
    }

    private String faukon() {
        String weightedProposition = weightedChoice(SENW_FAUKON);
        if(weightedProposition == null || "".equals(weightedProposition)){
            return weightedChoice(WORD_FAUKON);
        }
        return evaluateValues(weightedProposition);
    }

    private String eventualPostfixedAdverb() {
        String weightedProposition = weightedChoice(SENW_EVENTUAL_POSTFIXED_ADVERB);
        if(weightedProposition == null || "".equals(weightedProposition)){
            return weightedChoice(WORD_ADVERB_EVENTUAL_POSTFIXED);
        }
        return evaluateValues(weightedProposition);
    }

    private String personVerbAndDefiniteEnding(boolean plural, boolean infinitive) {
        String weightedProposition = weightedChoice(SENW_PERSON_VERB_AND_DEFINITE_ENDING);
        String inner = (weightedProposition == null || "".equals(weightedProposition))
                ? weightedChoice(WORD_PERSON_VERB_DEFINITE_ENDING)
                : evaluateValues(weightedProposition);
        if (infinitive) {
            return inner;
        }
        return buildPluralVerb(inner, plural);
    }

    private String proposition() {
        String weightedProposition = weightedChoice(SENW_PROPOSITION);
        return evaluateValues(weightedProposition);
    }

    private String articulatedPropositions() {
        String weightedProposition = weightedChoice(SENW_ARTICULATED_PROPOSITION);
        return evaluateValues(weightedProposition);
    }

    private String evaluateValues(final String template) {
        List<String> values = new ArrayList<>();
        Matcher matcher = VAR_PATTERN.matcher(template);
        while (matcher.find()){
            // ie: it will evaluate $thingAtom to it values
            values.add(templateFunction(matcher.group()));
        }
        String templateReplace = template.replaceAll(VAR_REGEXP, "%s");
        return String.format(templateReplace, values.toArray());
    }

    String templateFunction(final String templateName){
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
            case "$thingVerbHavingThingComplement" : result = thingVerbHavingThingComplement(false);break;
            case "$thingVerbHavingThingComplementPlural" : result = thingVerbHavingThingComplement(true);break;
            case "$thingWithRandomArticle" : result = addRandomArticle(thing(), false);break;
            case "$thingWithRandomArticlePlural" : result = addRandomArticle(thingPlural(), true);break;
            case "$thingWithRandomArticleRandom" : result = rand.nextBoolean()
                    ? addRandomArticle(thing(), false) : addRandomArticle(thingPlural(), true);break;
            case "$badThingWithRandomArticle" : result = addRandomArticle(badThings(), false);break;
            case "$badThingWithRandomArticlePlural" : result = addRandomArticle(badThings(), true);break;
            case "$person" : result = person();break;
            case "$personPlural" : result = personPlural();break;
            case "$personRandom" : result = rand.nextBoolean() ? person() : personPlural();break;
            case "$personInfinitiveVerbAndEnding" : result = personVerbAndEnding(true, true);break;
            case "$personVerbAndEnding" : result = personVerbAndEnding(false, false);break;
            case "$personVerbHavingPersonComplement" : result = personVerbHavingPersonComplement(false);break;
            case "$personVerbHavingPersonComplementPlural" : result = personVerbHavingPersonComplement(true);break;

            case "$personVerbHavingThingComplement" : result = personVerbHavingThingComplement(false, false);break;
            case "$personVerbHavingBadThingComplement" : result = personVerbHavingBadThingComplement(false);break;
            case "$personVerbHavingBadThingComplementPlural" : result = personVerbHavingBadThingComplement(true);break;

            case "$boss" : result = boss();break;
            case "$eventualAdverb" : result = eventualAdverb();break;
            case "$eventualPostfixedAdverb" : result = eventualPostfixedAdverb();break;
            case "$growthAtom" : result = growthAtom();break;
            case "$addIndefiniteArticleGrowth" : result = addIndefiniteArticle(growth(), false);break;
            case "$addIndefiniteArticleGrowthPlural" : result = addIndefiniteArticle(growth(), true);break;
            case "$addIndefiniteArticleThing" : result = addIndefiniteArticle(thing(), false);break;
            case "$addIndefiniteArticleThingPlural" : result = addIndefiniteArticle(thingPlural(), true);break;
            case "$addIndefiniteArticleThingRandom" : result = rand.nextBoolean()
                    ? addIndefiniteArticle(thing(), false) : addIndefiniteArticle(thingPlural(), true);break;
            case "$proposition" : result = proposition();break;
            case "$matrixOrSOPlural" : result = makeEventualPlural(matrixOrSO(), true);break;
            case "$matrixOrSO" : result = matrixOrSO();break;
            case "$timelessEvent": result = timelessEvent();break;
            case "$personVerbAndDefiniteEnding": result = personVerbAndDefiniteEnding(false, false);break;
            case "$personVerbAndDefiniteEndingInf": result = personVerbAndDefiniteEnding(false, true);break;
            case "$personVerbAndDefiniteEndingPlural": result = personVerbAndDefiniteEnding(true, false);break;
            case "$personVerbAndDefiniteEndingPluralInf": result = personVerbAndDefiniteEnding(true, true);break;

        }
        return result;
    }

    ///////////////// UTILITIES     ///////////////////

    private String makeEventualPlural(String word, boolean plural) {
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
        } else if (word.endsWith("y") && !word.toLowerCase().endsWith("ay") && !word.toLowerCase().endsWith("ey")
                && !word.toLowerCase().endsWith("iy") && !word.toLowerCase().endsWith("oy")
                && !word.toLowerCase().endsWith("uy")) {
            return word.substring(0, word.length() - 1) + "ies";
        }

        return word + "s";
    }

    private String buildPluralVerb(String verb, boolean plural) {
        int last = verb.trim().length() - 1;
        if ( plural || last < 0 ) {
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

    private String abbreviate(String s, double probability) {
        if (Math.random() < probability) {
            return s + " (" + sillyAbbreviationGeneratorSAS(s) + ")";
        }
        return s;
    }


    private String weightedChoice(String key) {
        return weightedChoice(dict.sentenceWithWeight(key));
    }
    private String weightedChoice(Map<String, Integer> choices) {
        int totalWeight = choices.values().stream().mapToInt(Integer::intValue).sum();
        if (totalWeight == 0) {
            return "";
        }

        int randomWeight = rand.nextInt(totalWeight);
        for (Map.Entry<String, Integer> entry : choices.entrySet()) {
            randomWeight -= entry.getValue();
            if (randomWeight < 0) {
                return entry.getKey();
            }
        }

        return "";
    }

    private static String cleanup(String text) {
        return text
                .replaceAll("\\s+", " ")
                .replaceAll(";\\.",".")
                .replaceAll(" ,", ",");
    }

}