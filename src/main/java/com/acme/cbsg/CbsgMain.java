package com.acme.cbsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.acme.cbsg.Constant.*;

public class CbsgMain {

    private static CbsgResourceUtil dict = new CbsgResourceUtil();

    public static void main(String... args) {
        // Corporate Bullshit Generator
        System.out.println(shortWorkshop());
    }

    public static Random rand = new Random();

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

        if (verb.charAt(last) == 's' || verb.charAt(last) == 'o' || verb.charAt(last) == 'z') {
            return verb.substring(0, last + 1) + "es" + verb.substring(last + 1);
        } else if (verb.charAt(last) == 'h') {
            if (verb.charAt(last - 1) == 'c' || verb.charAt(last - 1) == 's') {
                return verb.substring(0, last + 1) + "es" + verb.substring(last + 1);
            }
            return verb.substring(0, last + 1) + "s" + verb.substring(last + 1);
        } else if (verb.charAt(last) == 'y') {
            if (verb.charAt(last - 1) == 'a' || verb.charAt(last - 1) == 'e' || verb.charAt(last - 1) == 'i' || verb.charAt(last - 1) == 'o' || verb.charAt(last - 1) == 'u') {
                return verb.substring(0, last + 1) + "s" + verb.substring(last + 1);
            }
            return verb.substring(0, last) + "ies" + verb.substring(last + 1);
        }

        return verb.substring(0, last + 1) + "s" + verb.substring(last + 1);
    }

    public static String addIndefiniteArticle(String word, boolean plural) {
        if (plural) {
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

    public static String abbreviate(String s, double probability) {
        if (Math.random() < probability) {
            return s + " (" + sillyAbbreviationGeneratorSAS(s) + ")";
        }
        return s;
    }


    public static String weightedChoice(Map<String, Integer> choices) {
        int totalWeight = 0;
        for (int weight : choices.values()) {
            totalWeight += weight;
        }

        int randomWeight = new Random().nextInt(totalWeight);
        for (Map.Entry<String, Integer> entry : choices.entrySet()) {
            randomWeight -= entry.getValue();
            if (randomWeight < 0) {
                return entry.getKey();
            }
        }

        return null;
    }

    public static String boss() {
        String department = randomChoice(dict.stringList(WORD_BOSS_DEPARTMENT));
        Map<String, Integer> departmentOrTopRoleMap = new HashMap<>();
        departmentOrTopRoleMap.put("department", 42);
        departmentOrTopRoleMap.put("Visionary", 1);
        departmentOrTopRoleMap.put("Digital", 1);
        departmentOrTopRoleMap.put("Technical", 1);
        departmentOrTopRoleMap.put("Manifesto", 1);
        departmentOrTopRoleMap.put("Operating", 1);
        departmentOrTopRoleMap.put("Product", 1);
        departmentOrTopRoleMap.put("Scheme", 1);
        departmentOrTopRoleMap.put("Growth", 1);
        departmentOrTopRoleMap.put("Brand", 1);
        departmentOrTopRoleMap.put("Sales", 1);
        departmentOrTopRoleMap.put("Networking", 1);
        departmentOrTopRoleMap.put("Content", 1);
        departmentOrTopRoleMap.put("Holacracy", 1);
        departmentOrTopRoleMap.put("Data Protection", 1);
        departmentOrTopRoleMap.put("Risk Appetite", 1);
        departmentOrTopRoleMap.put("Business", 1);
        String departmentOrTopRole = weightedChoice(departmentOrTopRoleMap);

        if (rand.nextInt(4) == 1) {
            String managing = weightedChoice(Map.of(
                    "Managing ", 1, "Acting ", 1, "General", 1, "", 5));
            String vice = weightedChoice(Map.of(
                    "Vice ", 10, "Corporate Vice ", 1, "", 29));
            String co = weightedChoice(Map.of("Co-", 1, "", 4));
            String title = randomChoice(List.of(
                    vice + co + "Director", co + "Chief", co + "Head",
                    vice + co + "President", "Supervisor", co + "Manager"
            ));
            String age = weightedChoice(Map.of("Senior ", 1, "", 3));
            String exec_ = weightedChoice(Map.of("Excutive ", 1, "Principal ", 1, "", 10));
            return managing + age + exec_ + title + " of " + department;
        }
        String groupal = weightedChoice(Map.of(
                "Group ", 1, "Global ", 1, "", 18
        ));
        String officerOrCatalyst = weightedChoice(Map.of(
                        "Catalyst", 1, "Futurist", 1, "Strategist", 1, "Technologist", 1,
                        "Evangelist", 1, "Officer", 15
                )
        );
        return groupal + abbreviate("Chief " + departmentOrTopRole + " " +
                officerOrCatalyst, 0.6);
    }

    public static String person(boolean plural) {
        if (!plural) {
            int r = rand.nextInt(46);
            if (r == 1) {
                return thingAtom(rand.nextBoolean()) + " champion";
            } else if (r <= 32) {
                return randomChoice(dict.stringList(WORD_PERSON_NOT_PLURAL));
            }
            return boss();
        }
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
        int r = rand.nextInt(270);

        if (r <= 194) {
            return matrixOrSO();
        }
        String veryImportantAbbreviation = "";
        // this 5 can be anything
        switch (r) {
            case 195:
                veryImportantAbbreviation = abbreviate("Management Information System", 0.5);
                break;
            case 196:
                veryImportantAbbreviation = abbreviate("Management Information System", 0.5);
                break;
            case 197:
                veryImportantAbbreviation = abbreviate("Quality Management System", 0.5);
                break;
            case 198:
                veryImportantAbbreviation = abbreviate("Control Information System", 0.5);
                break;
            case 199:
                veryImportantAbbreviation = abbreviate("Strategic Management System", 0.5);
                break;
            case 200:
                veryImportantAbbreviation = abbreviate("Business Model Innovation", 1.0);
                break;
            case 201:
                veryImportantAbbreviation = abbreviate("leadership development system", 0.5);
                break;
        }

        if (r < 202) {
            return veryImportantAbbreviation;
        }

        return randomChoice(dict.stringList(WORD_THING_INNER));
    }

    private static String matrixOrSO() {
        return weightedChoice(Map.of(
                "organization", 2,
                "silo", 3,
                "matrix", 3,
                "cube", 1,
                "sphere", 1,
                "pyramid", 1));
    }

    public static String thingAtom(boolean plural) {
        if (!plural) {
            int r = rand.nextInt(471);
            String thing = "";
            // todo: parameterized
            switch (r) {
                case 1:
                    thing = timelessEvent();
                    break;
                case 2:
                    thing = abbreviate("Quality Research", 0.5);
                    break;
                case 3:
                    thing = abbreviate("Customer Experience", 0.5);
                    break;
                case 4:
                    thing = abbreviate("Customer Experience Management", 0.5);
                    break;
                default:
                    thing = randomChoice(dict.stringList(WORD_THING_ATOM));
            }
            if (r < 201) {
                return thing;
            } else {
                return thingInner();
            }
        } else {
            int r = rand.nextInt(310);
            if (r <= 40) {
                return randomChoice(dict.stringList(WORD_THING_ATOM_PLURAL));
            } else {
                return makeEventualPlural(thingInner(), true);
            }
        }

    }


    public static String badThings() {
        return randomChoice(dict.stringList(WORD_BAD_THINGS));
    }

    public static String thingAdjective() {
        return randomChoice(dict.stringList(WORD_THING_ADJECTIVE));
    }

    public static String eventualAdverb() {
        if (rand.nextInt(4) == 1) {
            return randomChoice(dict.stringList(WORD_ADVERB_EVENTUAL)) + " ";
        }
        return "";
    }

    public static String thing(boolean plural) {
        int r = rand.nextInt(160);
        if (r < 10) {
            return (thingAdjective() + ", " + thingAdjective() + " " +
                    thingAtom(plural));
        } else if (r < 15) {
            return (thingAdjective() + " " +
                    thingAtom(plural));
        } else if (r < 80) {
            return (thingAdjective() + " and " + thingAdjective() + " " +
                    thingAtom(plural));
        } else if (r < 82) {
            return (thingAdjective() + " and/or " + thingAdjective() + " " +
                    thingAtom(plural));
        } else if (r < 84) {
            return growth();
        } else if (r < 90) {
            return (thingAdjective() + ", " + thingAdjective() + " and " +
                    thingAdjective() + " " + thingAtom(plural));
        } else if (r < 94) {
            return (thingAdjective() + " " +
                    thingAtom(plural));
        }
        return thingAtom(plural);
    }

//    static Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u');
//    public static String addIndefiniteArticle(String word, boolean plural) {
//        if(plural){
//            return word;
//        }
//        if(vowels.contains(word.toLowerCase().charAt(0))){
//            return "an " + word;
//        }
//        return "a " + word;
//    }

    public static String addRandomArticle(String word, boolean plural) {
        int r = rand.nextInt(15);
        if (r <= 2) {
            return "the " + word;
        } else if (r < 6) {
            return "our " + word;
        }
        return addIndefiniteArticle(word, plural);
    }

    public static String thingWithRandomArticle(boolean plural) {
        if (!plural && rand.nextInt(100) == 1) {
            return "the 'why' behind " + thing(rand.nextBoolean());
        }
        return addRandomArticle(thing(plural), plural);
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
        String inner = randomChoice(List.of(
                "address", "identify", "avoid", "mitigate", "minimize", "overcome",
                "tackle", "reduce", "alleviate", "filter out", "remove", "prevent"
        ));
        return buildPluralVerb(inner, plural);
    }

    public static String personVerbHavingPersonComplement(boolean plural) {
        String inner = randomChoice(List.of(
                "motivate", "target", "enable", "drive", "synergize", "empower",
                "prioritize", "incentivise", "inspire", "transfer", "promote",
                "influence", "strengthen", "energize", "invigorate", "reenergize"
        ));
        return buildPluralVerb(inner, plural);
    }

    public static String thingVerbAndDefiniteEnding(boolean plural) {
        String inner = randomChoice(List.of(
                "add value", "deliver maximum impact", "be on track"
        ));
        return buildPluralVerb(inner, plural);
    }

    public static String thingVerbHavingThingComplement(boolean plural) {
        String inner = randomChoice(dict.stringList(WORD_THING_VERB_HAVING_COMPLEMENT));
        return buildPluralVerb(inner, plural);
    }

    public static String thingVerbAndEnding(boolean plural) {
        boolean compl_sp = rand.nextBoolean();
        int r = rand.nextInt(103);
        if (r < 55) {
            return (thingVerbHavingThingComplement(plural) + " " +
                    thingWithRandomArticle(compl_sp));
        } else if (r < 100) {
            return (personVerbHavingPersonComplement(plural) + " the " +
                    person(compl_sp));
        }
        return thingVerbAndDefiniteEnding(plural);
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
        return (personVerbHavingThingComplement(plural, infinitive) + " " +
                thingWithRandomArticle(compl_sp));
    }

    public static String personInfinitiveVerbAndEnding() {
        return personVerbAndEnding(true, true);
    }

    public static String faukon() {
        int r = rand.nextInt(16);
        if (r < 15) {
            return randomChoice(dict.stringList(WORD_FAUKON));
        }
        return "we must activate the " + matrixOrSO() + " to";
    }

    public static String eventualPostfixedAdverb() {
        boolean plural = rand.nextBoolean();
        int r = rand.nextInt(255);
        if (r <= 38) {
            return randomChoice(dict.stringList(WORD_ADVERB_EVENTUAL_POSTFIXED));
        }
        switch (r) {
            case 39:
                return " using " + thingWithRandomArticle(plural);
            case 40:
                return " by leveraging " + thingWithRandomArticle(plural);
            case 41:
                return " taking advantage of " + thingWithRandomArticle(plural);
            case 42:
                return " within the " + matrixOrSO();
            case 43:
                return " across the " + makeEventualPlural(matrixOrSO(), true);
            case 44:
                return (" across and beyond the " +
                        makeEventualPlural(matrixOrSO(), true));
            case 45:
                return " resulting in " + addIndefiniteArticle(growth(), false);
            case 46:
                return " reaped from our " + growth();
            case 47:
                return (" as a consequence of " +
                        addIndefiniteArticle(growth(), false));
            case 48:
                return (" because " + thingWithRandomArticle(plural) + " " +
                        buildPluralVerb("produce", plural) + " " + growth());
            case 49:
                return " up, down and across the " + matrixOrSO();
            case 50:
                return " ensuring " +
                        addIndefiniteArticle(thing(plural), plural);
            case 51:
                return ", paving the way for " +
                        addIndefiniteArticle(thing(plural), plural);

        }
        return "";
    }

    public static String personVerbAndDefiniteEnding(boolean plural, boolean infinitive) {
        int r = rand.nextInt(113);
        String inner = "";
        switch (r) {
            case 1:
                inner = ("create an environment where " +
                        thingAtom(false) + ", " +
                        thingAtom(false) + " and " +
                        thingAtom(false) + " can thrive");
                break;
            case 2:
                inner = "advance our strategy to " + personInfinitiveVerbAndEnding();
                break;
            case 3:
                inner = ("focus on our " + thingAtom(plural) + " to " +
                        personInfinitiveVerbAndEnding());
                break;
            default:
                inner = randomChoice(dict.stringList(WORD_PERSON_VERB_DEFINITE_ENDING));
        }
        if (infinitive) {
            return inner;
        }
        return buildPluralVerb(inner, plural);
    }

    public static String proposition() {
        boolean plural = rand.nextBoolean();
        int r = rand.nextInt(116);
        if (r <= 5) {
            return (faukon() + " " + personInfinitiveVerbAndEnding() +
                    eventualPostfixedAdverb());
        } else if (r <= 50) {
            return ("the " + person(plural) + " " + eventualAdverb() +
                    personVerbAndEnding(plural, false) +
                    eventualPostfixedAdverb());
        } else if (r <= 92) {
            return (thingWithRandomArticle(plural) + " " + eventualAdverb() +
                    thingVerbAndEnding(plural) + eventualPostfixedAdverb());
        } else if (r <= 97) {
            return (thingAtom(false) + " and " + thingAtom(false) + " " +
                    eventualAdverb() + thingVerbAndEnding(true) +
                    eventualPostfixedAdverb());
        } else if (r <= 100) {
            return (thingAtom(false) + ", " + thingAtom(false) + " and " +
                    thingAtom(false) + " " +
                    eventualAdverb() + thingVerbAndEnding(true) +
                    eventualPostfixedAdverb());
        } else if (r <= 101) {
            return ("there can be no " + growthAtom() +
                    " until we can achieve " +
                    addIndefiniteArticle(growth(), false));
        } else if (r <= 103) {
            return thing(false) + " is all about " + thing(plural);
        } else if (r <= 104) {
            return "there is no alternative to " + thingAtom(plural);
        } else if (r <= 105) {
            return "the key to " + thingAtom(false) + " is " + thingAtom(false);
        } else if (r <= 106) {
            return "opting out of " + thing(plural) + " is not a choice";
        } else if (r <= 107) {
            return (addIndefiniteArticle(growth(), false) +
                    " goes hand-in-hand with " +
                    addIndefiniteArticle(growth(), false));
        } else if (r <= 108) {
            return ("the " + person(plural) + " will be well equipped to " +
                    personInfinitiveVerbAndEnding());
        } else if (r <= 109) {
            return thingAtom(false) + " is a matter of speed of action";
        } else if (r <= 110) {
            return (thingAtom(false) + " won't happen without " +
                    thingAtom(plural));
        } else if (r <= 111) {
            return (thingWithRandomArticle(false) +
                    " will be best positioned to " +
                    personInfinitiveVerbAndEnding());
        } else if (r <= 112) {
            return (thingAtom(false) + " in the digital age calls for " +
                    thingAtom(plural));
        } else if (r <= 113) {
            return thingAtom(false) + " moves the company up the value chain";
        }
        //else if (r<=114) {
        return (thingAtom(false) +
                " requires that we all pull in the same direction");
        //}
    }

    public static String articulatedPropositions() {
        int r = rand.nextInt(416);
        if (r <= 270) {
            return proposition();
        } else if (r <= 280) {
            return proposition() + "; this is why " + proposition();
        } else if (r <= 290) {
            return proposition() + "; nevertheless " + proposition();
        } else if (r <= 300) {
            return proposition() + ", whereas " + proposition();
        } else if (r <= 340) {
            return proposition() + ", while " + proposition();
        } else if (r <= 350) {
            return proposition() + ". In the same time, " + proposition();
        } else if (r <= 360) {
            return proposition() + ". As a result, " + proposition();
        } else if (r <= 370) {
            return proposition() + ", whilst " + proposition();
        } else if (r <= 373) {
            return "our gut-feeling is that " + proposition();
        } else if (r <= 376) {
            return ("the point is not merely to " +
                    personInfinitiveVerbAndEnding() +
                    ". The point is to " + personInfinitiveVerbAndEnding())
                    ;
        } else if (r <= 380) {
            return ("it's not about " + thingAtom(rand.nextBoolean()) +
                    ". It's about " +
                    thingWithRandomArticle(rand.nextBoolean()))
                    ;
        } else if (r <= 383) {
            return ("our challenge is not to " +
                    personInfinitiveVerbAndEnding() +
                    ". Our challenge is to " +
                    personInfinitiveVerbAndEnding())
                    ;
        } else if (r <= 386) {
            return "going forward, " + proposition();
        } else if (r <= 389) {
            return "actually, " + proposition();
        } else if (r <= 392) {
            return "in the future, " + proposition();
        } else if (r <= 395) {
            return "flat out, " + proposition();
        } else if (r <= 398) {
            return "first and foremost, " + proposition();
        } else if (r <= 402) {
            return ("the game is all about " +
                    thingAtom(false) + ", " +
                    thingAtom(false) + ", " +
                    thingAtom(false) + ", " +
                    thingAtom(false) + ", and " +
                    thingAtom(false) + " - not " +
                    thingAtom(false) + ", " +
                    thingAtom(false) + ", " +
                    thingAtom(false) + ", " +
                    thingAtom(false) + ", and " +
                    thingAtom(false))
                    ;
        } else if (r == 403) {
            return "in today's fast-changing world, " + proposition();
        } else if (r == 404) {
            return "internally and externally, " + proposition();
        } else if (r == 405) {
            return "our message is: " + proposition();
        } else if (r == 406) {
            return "in a data-first world, " + proposition();
        } else if (r == 407) {
            return "the future awaits"
                    ;
        } else if (r == 408) {
            return (thingAtom(true) +
                    " not only thrive on change, they initiate it")
                    ;
        } else if (r == 409) {
            return ("as the pace of " + thingAtom(rand.nextBoolean()) +
                    " continues to accelerate, " + thingAtom(false) +
                    " has become a necessity")
                    ;
        } else if (r == 410) {
            return (thingAtom(false) + ", " +
                    thingAtom(false) + ", " +
                    thingAtom(false) + ", " +
                    thingAtom(false) +
                    " - all are competing for the attention of " +
                    person(true))
                    ;
        } else if (r == 411) {
            return "success brings success";
        } else if (r == 412) {
            return ("everyone is coming to grips with the fact that " +
                    proposition());
        } else if (r == 413) {
            return (thing(true) +
                    " will be a thing of the past over the next decade" +
                    " and be fully replaced with " +
                    thing(rand.nextBoolean()));
        } else if (r == 414) {
            return ("as the consumer and commerce landscape continues to evolve, " +
                    proposition());
        } else if (r == 415) {
            return "in an age of information, " + proposition();
        } else if (r == 416) {
            return "in a growing digital environment, " + proposition();
        }
        return "";
    }

    public static String sentence() {
        String propositions = articulatedPropositions();
        return propositions.substring(0, 1).toUpperCase() + propositions.substring(1) + ".";
    }

    public static String sentences() {
        StringBuilder ret = new StringBuilder();
        int pm = rand.nextInt(10);
        int limit = (rand.nextBoolean()) ? 30 + pm : 30 - pm;
        int until = Math.max(3, limit);
        int cnt = 0;
        // average 30 sentence +-10
        while (until != cnt) {
            ret.append(sentence()).append(" ");
            cnt++;
        }
        return ret.toString();
    }


    public static String sentenceGuaranteedAmount(int count) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < count; i++) {
            ret.append(sentence()).append(" ");
        }
        return ret.toString();
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

}