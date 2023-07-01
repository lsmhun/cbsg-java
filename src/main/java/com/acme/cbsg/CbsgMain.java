package com.acme.cbsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CbsgMain {

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
//
//    public static String weightedChoice(Map<String, Double> choices) {
//        double total = 0;
//        for (double value : choices.values()) {
//            total += value;
//        }
//
//        double r = Math.random() * total;
//        double upto = 0;
//        for (Map.Entry<String, Double> entry : choices.entrySet()) {
//            String c = entry.getKey();
//            double w = entry.getValue();
//            if (upto + w > r) {
//                return c;
//            }
//            upto += w;
//        }
//
//        return null;
//    }

//    public static String boss() {
//        String[] departments = {"Human Resources", "Controlling"};
//        String department = departments[new Random().nextInt(departments.length)];
//
//        Map<String, Integer> departmentOrTopRole = new HashMap<>();
//        departmentOrTopRole.put(department, 42);
//        departmentOrTopRole.put("Visionary", 1);
//        departmentOrTopRole.put("Digital", 1);
//        departmentOrTopRole.put("Technical", 1);
//        departmentOrTopRole.put("Manifesto", 1);
//        departmentOrTopRole.put("Operating", 1);
//        departmentOrTopRole.put("Product", 1);
//        departmentOrTopRole.put("Scheme", 1);
//        departmentOrTopRole.put("Growth", 1);
//        departmentOrTopRole.put("Brand", 1);
//        departmentOrTopRole.put("Sales", 1);
//        departmentOrTopRole.put("Networking", 1);
//        departmentOrTopRole.put("Content", 1);
//        departmentOrTopRole.put("Holacracy", 1);
//        departmentOrTopRole.put("Data Protection", 1);
//        departmentOrTopRole.put("Risk Appetite", 1);
//        departmentOrTopRole.put("Business", 1);
//
//        String departmentOrTopRoleResult = weightedChoice(departmentOrTopRole);
//
//        if (new Random().nextInt(4) == 0) {
//            Map<String, Integer> managing = new HashMap<>();
//            managing.put("Managing ", 1);
//            managing.put("Acting ", 1);
//            managing.put("General", 1);
//            managing.put("", 5);
//
//            Map<String, Integer> vice = new HashMap<>();
//            vice.put("Vice ", 10);
//            vice.put("Corporate Vice ", 1);
//            vice.put("", 29);
//
//            Map<String, Integer> co = new HashMap<>();
//            co.put("Co-", 1);
//            co.put("", 4);
//
//            String managingResult = weightedChoice(managing);
//            String viceResult = weightedChoice(vice);
//            String coResult = weightedChoice(co);
//
//            String[] titles = {viceResult + coResult + "Director", coResult + "Chief", coResult + "Head",
//                    viceResult + coResult + "President", "Supervisor", coResult + "Manager"};
//            String title = titles[new Random().nextInt(titles.length)];
//
//            Map<String, Integer> age = new HashMap<>();
//            age.put("Senior ", 1);
//            age.put("", 3);
//
//            Map<String, Integer> exec_ = new HashMap<>();
//            exec_.put("Excutive ", 1);
//            exec_.put("Principal ", 1);
//            exec_.put("", 10);
//
//            String ageResult = weightedChoice(age);
//            String execResult = weightedChoice(exec_);
//
//            return managingResult + ageResult + execResult + title + " of " + department;
//        }
//
//        Map<String, Integer> groupal = new HashMap<>();
//        groupal.put("Group ", 1);
//        groupal.put("Global ", 1);
//        groupal.put("", 18);
//
//        Map<String, Integer> officerOrCatalyst = new HashMap<>();
//        officerOrCatalyst.put("Catalyst", 1);
//        officerOrCatalyst.put("Futurist", 1);
//        officerOrCatalyst.put("Strategist", 1);
//        officerOrCatalyst.put("Technologist", 1);
//        officerOrCatalyst.put("Evangelist", 1);
//        officerOrCatalyst.put("Officer", 15);
//
//        String groupalResult = weightedChoice(groupal);
//        String officerOrCatalystResult = weightedChoice(officerOrCatalyst);
//
//        return groupalResult + abbreviate("Chief " + departmentOrTopRoleResult + " " +
//                officerOrCatalystResult, 0.6);
//    }


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
        String department = randomChoice(List.of(
                "steering committee", "group", "project manager", "community",
                "sales manager", "enabler", "powerful champion",
                "thought leader", "gatekeeper", "resource",
                "senior support staff", "brand manager", "category manager",
                "account executive", "project leader", "product manager",
                "naming committee", "executive committee",
                "white-collar workforce", "innovator", "game changer",
                "visionary", "market thinker", "network", "initiator",
                "change agent", "rockstar", "facilitator", "disruptor",
                "challenger", "six-sigma black belt"
        ));
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
                return randomChoice(List.of(
                        "steering committee", "group", "project manager", "community",
                        "sales manager", "enabler", "powerful champion",
                        "thought leader", "gatekeeper", "resource",
                        "senior support staff", "brand manager", "category manager",
                        "account executive", "project leader", "product manager",
                        "naming committee", "executive committee",
                        "white-collar workforce", "innovator", "game changer",
                        "visionary", "market thinker", "network", "initiator",
                        "change agent", "rockstar", "facilitator", "disruptor",
                        "challenger", "six-sigma black belt"
                ));
            }
            return boss();
        }
        return randomChoice(List.of(
                "key people", "human resources", "customers", "clients", "resources",
                "team players", "enablers", "stakeholders", "standard-setters",
                "partners", "business leaders", "thinkers/planners",
                "white-collar workers", "board-level executives",
                "key representatives", "innovators", "policy makers", "pioneers",
                "game changers", "market thinkers", "thought leaders", "mediators",
                "facilitators", "attackers", "initiators", "decision makers",
                "Growth Hackers", "Digital Marketers", "Creative Technologists",
                "Products Managers", "Products Owners", "disruptors", "challengers"
        ));
    }


    public static String timelessEvent() {
        return randomChoice(List.of("kick-off", "roll-out", "client event", "quarterly results"));
    }

    public static String growthAtom() {
        return randomChoice(List.of(
                "growth", "improvement", "throughput increase", "efficiency gain",
                "yield enhancement", "expansion", "productivity improvement",
                "gain in task efficiency", "shift in value", "increase in margins",
                "cost reduction", "cost effectiveness", "level of change",
                "revenue growth", "profits growth", "growth momentum",
                "increase in sales", "run-rate efficiency"
        ));
    }

    public static String growth() {
        String superlative = randomChoice(List.of(
                "organic", "double-digit", "upper single-digit", "breakout",
                "unprecedented", "unparallelled", "proven", "measured", "sustained",
                "sustainable", "robust", "solid", "rock-solid", "healthy",
                "incremental", "significant", "recurring", "sizeable", "rapid",
                "breakneck", "profitable", "disciplined", "accelerated", "impressive",
                "superior", "attractive-enough", "continual", "above-potential",
                "better-than-average", "exponential", "long-term", "future"
        ));
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

        return randomChoice(List.of(
                "mission", "vision", "guideline", "roadmap", "timeline",
                "win-win solution", "baseline starting point", "sign-off",
                "escalation", "system", "planning", "target", "calibration",
                "process", "talent", "execution", "leadership", "performance",
                "solution provider", "value", "value creation",
                "value realization", "document", "bottom line", "momentum",
                "opportunity", "credibility", "issue", "core meeting", "platform",
                "niche", "content", "communication", "goal", "value creation goal",
                "alternative", "culture", "requirement", "potential", "challenge",
                "empowerment", "benchmarking", "framework", "benchmark",
                "implication", "integration", "enabler", "control", "trend",
                "business case", "architecture", "action plan", "project",
                "review cycle", "trigger event", "strategy formulation",
                "decision", "enhanced data capture", "energy", "plan",
                "initiative", "priority", "synergy", "incentive", "dialogue",
                "concept", "time-phase", "projection", "blended approach",
                "low hanging fruit", "forward planning", "pre-plan", "pipeline",
                "bandwidth", "workshop", "paradigm", "paradigm shift",
                "strategic staircase", "cornerstone", "executive talent",
                "evolution", "workflow", "message", "risk/return profile",
                "efficient frontier", "pillar", "internal client", "consistency",
                "on-boarding process", "dotted line", "action item",
                "cost efficiency", "channel", "convergence", "infrastructure",
                "metric", "technology", "relationship", "partnership",
                "supply-chain", "portal", "solution", "business line",
                "white paper", "scalability", "innovation", "Balanced Scorecard",
                "key differentiator", "competitive differentiator",
                "idiosyncrasy", "benefit", "say/do ratio", "segmentation",
                "image", "business model", "business philosophy", "branding",
                "methodology", "profile", "measure", "measurement", "philosophy",
                "branding strategy", "efficiency", "industry", "commitment",
                "perspective", "risk appetite", "best practice",
                "brand identity", "customer centricity", "shareholder value",
                "attitude", "mindset", "flexibility", "granularity", "engagement",
                "pyramid", "market", "diversity", "interdependency", "scaling",
                "asset", "flow charting", "value proposition",
                "performance culture", "change", "reward", "learning",
                "next step", "delivery framework", "structure",
                "support structure", "standardization", "objective", "footprint",
                "transformation process", "policy", "sales target", "ecosystem",
                "market practice", "atmosphere", "operating strategy",
                "core competency", "market practice", "operating strategy",
                "insight", "accomplishment", "correlation", "touch point",
                "knowledge transfer", "correlation", "capability", "gamification",
                "smooth transition", "leadership strategy", "collaboration",
                "success factor", "lever", "breakthrough", "open-door policy",
                "recalibration", "wow factor", "onboarding solution",
                "brand pyramid", "dashboard", "branding",
                "local-for-local strategy", "cross-sell message",
                "up-sell message", "divisional structure", "value chain",
                "microsegment", "rollout plan", "architectural approach",
                "brand value", "milestone", "co-innovation", "speedup",
                "validation", "skill", "skillset", "feedback", "learnability",
                "visibility", "agility", "simplification", "digitization",
                "streamlining", "brainstorming space", "crowdsourcing",
                "big-bang approach", "execution message", "criticality",
                "opportunity pipeline", "reorganization", "synergization",
                "socialization", "strategic shift", "growth engine", "tailwind",
                "accelerator", "deliverable", "takeaway", "insourcing",
                "outsourcing", "careful consideration", "conviction", "initiator",
                "operating model", "proof-point", "bounce rate",
                "marketing funnel", "offshoring", "quick-win", "cross-pollination",
                "hybridation", "positioning", "reinvention", "functionality",
                "mindshare", "mobility space", "decision-to-execution cycle",
                "adjustment", "force management program", "launchpad",
                "value-chain", "motion", "customer-orientation", "realignment",
                "governmentalization", "case study", "blockchain",
                "Innovation Incubator", "input", "scope", "action", "context",
                "next level", "topology", "data point", "enablement",
                "test-first design"
        ));
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
                    thing = randomChoice(List.of(
                            "team building", "focus", "strategy",
                            "planning granularity", "core business", "implementation",
                            "intelligence", "change management", "ROE", "EBITDA",
                            "enterprise content management", "excellence", "trust",
                            "respect", "openness", "transparency", "decision making",
                            "risk management", "enterprise risk management", "leverage",
                            "diversification", "successful execution",
                            "effective execution", "selectivity", "optionality",
                            "expertise", "awareness", "broader thinking", "client focus",
                            "thought leadership", "quest for quality",
                            "360-degree thinking", "drill-down", "impetus", "fairness",
                            "intellect", "emotional impact", "emotional intelligence",
                            "adaptability", "stress management", "self-awareness",
                            "strategic thinking", "cross-fertilization", "effectiveness",
                            "SWOT analysis", "responsibility", "accountability", "ROI",
                            "line of business", "serviceability", "responsiveness",
                            "simplicity", "portfolio shaping", "knowledge sharing",
                            "continuity", "visual thinking", "interoperability",
                            "compliance", "teamwork", "self-efficacy", "decision-maker",
                            "line-of-sight", "scoping", "line-up", "predictability",
                            "recognition", "investor confidence", "competitive advantage",
                            "uniformity", "competitiveness", "big picture",
                            "resourcefulness", "quality", "upside focus", "sustainability",
                            "resiliency", "social sphere", "intuitiveness",
                            "effectiveness", "competitiveness", "resourcefulness",
                            "informationalization", "role building", "talent retention",
                            "innovativeness", "Economic Value Creation",
                            "intellectual capital", "high quality",
                            "full range of products", "technical strength",
                            "quality assurance", "specification quality",
                            "market environment", "client perspective",
                            "solution orientation", "client satisfaction", "integrity",
                            "reputation", "time-to-market", "innovative edge",
                            "book value growth", "global network", "ability to deliver",
                            "active differentiation", "solid profitability",
                            "core capacity", "digital economy",
                            "white-collar productivity", "white-collar efficiency",
                            "governance", "corporate governance", "business development",
                            "corporate identity", "attractiveness", "design philosophy",
                            "global footprint", "risk taking", "focus on speed",
                            "business equation", "edge", "ownership",
                            "competitive success", "discipline", "knowledge management",
                            "ability to move fast", "ingenuity", "insightfulness",
                            "integrativeness", "customer footprint", "time-to-value",
                            "efficacy", "DNA", "dedication", "franchise", "global reach",
                            "global touch-base", "technical excellence",
                            "values congruence", "purpose", "catalyst for growth",
                            "goal setting", "craftsmanship", "operational excellence",
                            "re-engineering", "mindfulness", "quality thinking",
                            "user experience", "speed of execution", "responsive design",
                            "readiness to go 'all-in'", "machine intelligence",
                            "creativity", "can-do attitude", "relevance", "disruption",
                            "dematerialization", "disintermediation", "disaggregation",
                            "wave of change", "digitalization", "CAPEX",
                            "window of opportunity", "beta", "coopetition",
                            "digital change", "business excellence", "business impact",
                            "business acumen", "leadership culture", "glocalization",
                            "re-equitizing", "cost rationalization",
                            "strategic optionality", "product expertise", "velocity",
                            "elasticity", "value stream management",
                            "digital acceleration", "quality control", "decision-making",
                            "digital business", "Organizational Intelligence",
                            "Business Intelligence", "self-actualization",
                            "leadership effectiveness", "customer's journey",
                            "adding services", "centerpiece", "modern simplicity",
                            "cost control", "operations delivery", "guidance",
                            "onboarding", "cost structure", "traction", "ethos",
                            "auditability"
                    ));
            }
            if (r < 201) {
                return thing;
            } else {
                return thingInner();
            }
        } else {
            int r = rand.nextInt(310);
            if (r <= 40) {
                return randomChoice(List.of(
                        "key target markets", "style guidelines",
                        "key performance indicators", "market conditions",
                        "market forces", "market opportunities", "tactics",
                        "organizing principles", "interpersonal skills",
                        "roles and responsibilities", "cost savings",
                        "lessons learned", "client needs", "requests / solutions",
                        "mobile strategies", "expectations and allocations",
                        "workshops", "dynamics", "options", "aspirations",
                        "swim lanes", "pockets of opportunities",
                        "social implications", "analytics", "advanced analytics",
                        "growth years", "big data", "adjacencies", "core competences",
                        "strengths", "corporate values", "core values",
                        "competitive dynamics", "workforce adjustments",
                        "lessons learned", "core verticals", "metrics",
                        "cost-control measures", "expectations", "data practices"
                ));
            } else {
                return makeEventualPlural(thingInner(), true);
            }
        }

    }


    public static String badThings() {
        return randomChoice(List.of(
                "issues", "intricacies", "organizational diseconomies", "black swans",
                "challenging market conditions", "inefficiencies", "overlaps",
                "known unknowns", "unknown unknowns", "soft cycle issues", "obstacles",
                "surprises", "weaknesses", "threats", "barriers to success",
                "barriers", "barriers to growth", "problems", "uncertainties",
                "unfavorable developments", "consumer/agent disconnects",
                "underperforming areas", "information overloads", "concerns",
                "shortfalls", "limitations", "downtimes", "headwinds",
                "subpar returns", "gaps", "market gaps", "capability gaps",
                "constraints", "problems/difficulties", "bottlenecks",
                "misunderstandings", "dilemmas", "interdependencies",
                "discontinuities", "hiccups", "vulnerabilities",
                "negative cash flows", "net profit revenue deficiencies",
                "negative contributions to profits", "shortcomings", "pitfalls",
                "friction", "red flags", "roadblocks", "decision-making biases"
        ));
    }

    public static String thingAdjective() {
        return randomChoice(List.of(
                "efficient", "strategic", "constructive", "proactive", "strong",
                "key", "global", "corporate", "cost-effective", "focused", "top-line",
                "credible", "agile", "holistic", "new", "adaptive", "optimal",
                "unique", "core", "compliant", "goal-oriented", "non-linear",
                "problem-solving", "prioritizing", "cultural", "future-oriented",
                "potential", "versatile", "leading", "dynamic", "progressive",
                "non-deterministic", "informed", "leveraged", "challenging",
                "intelligent", "controlled", "educated", "non-standard", "underlying",
                "centralized", "decentralized", "reliable", "consistent", "competent",
                "prospective", "collateral", "functional", "tolerably expensive",
                "organic", "forward-looking", "next-level", "executive", "seamless",
                "spectral", "balanced", "effective", "integrated", "systematized",
                "parallel", "responsive", "synchronized", "carefully-designed",
                "carefully thought-out", "cascading", "high-level", "siloed",
                "operational", "future-ready", "flexible", "movable", "right",
                "productive", "evolutionary", "overarching", "documented", "awesome",
                "coordinated", "aligned", "enhanced", "control-based",
                "industry-standard", "accepted", "agreed-upon", "target",
                "customer-centric", "wide-spectrum", "well-communicated",
                "cutting-edge", "state-of-the-art", "verifiable", "six-sigma", "solid",
                "inspiring", "growing", "market-altering", "vertical", "emerging",
                "differentiating", "integrative", "cross-functional", "measurable",
                "well-planned", "accessible", "actionable", "accurate", "insightful",
                "relevant", "long-term", "longer-term", "tactical", "best-of-breed",
                "robust", "targeted", "personalized", "interactive", "streamlined",
                "transparent", "traceable", "far-reaching", "powerful", "improved",
                "executive-level", "goal-based", "top-level", "cooperative",
                "value-adding", "streamlining", "time-honored", "idiosyncratic",
                "sustainable", "in-depth", "immersive", "cross-industry",
                "time-phased", "day-to-day", "present-day", "modern-day",
                "profit-maximizing", "generic", "granular", "values-based",
                "value-driven", "well-defined", "outward-looking", "scalable",
                "strategy-focused", "promising", "collaborative", "scenario-based",
                "principle-based", "vision-setting", "client-oriented",
                "long-established", "established", "organizational", "visionary",
                "trusted", "full-scale", "firm-wide", "fast-growth",
                "performance-based", "data-inspired", "high-performance",
                "cross-enterprise", "outsourced", "situational", "bottom-up",
                "multidisciplinary", "one-to-one", "goal-directed",
                "intra-organisational", "high-performing", "multi-source",
                "360-degree", "motivational", "differentiated", "solutions-based",
                "compelling", "structural", "go-to-market", "on-message",
                "productivity-enhancing", "value-enhancing", "mission-critical",
                "business-enabling", "transitional", "future", "game-changing",
                "enterprise-wide", "rock-solid", "bullet-proof", "superior",
                "genuine", "alert", "nimble", "phased", "selective", "macroscopic",
                "low-risk high-yield", "interconnected", "high-margin", "resilient",
                "high-definition", "well-crafted", "fine-grained", "context-aware",
                "multi-tasked", "feedback-based", "analytics-based", "fact-based",
                "usage-based", "multi-channel", "omni-channel", "cross-channel",
                "specific", "heart-of-the-business", "responsible",
                "socially conscious", "results-centric", "business-led",
                "well-positioned", "end-to-end", "high-quality", "siloed", "modular",
                "service-oriented", "competitive", "scale-as-you-grow", "outside-in",
                "hyper-hybrid", "long-running", "large-scale", "wide-ranging",
                "wide-range", "stellar", "dramatic", "aggressive", "innovative",
                "high-powered", "above-average", "result-driven", "innovation-driven",
                "customized", "outstanding", "non-mainstream", "customer-facing",
                "consumer-facing", "unified", "cooperative", "laser-focused",
                "well-implemented", "diversifying", "market-changing",
                "metrics-driven", "pre-integrated", "solution-oriented", "impactful",
                "world-class", "front-end", "leading-edge", "cost-competitive",
                "extensible", "under-the-radar", "high-grade", "structured",
                "trust-based", "intra-company", "inter-company", "profit-oriented",
                "sizeable", "highly satisfactory", "bi-face", "tri-face", "disruptive",
                "technological", "marketplace", "fast-evolving", "open",
                "fully networked", "adoptable", "trustworthy", "science-based",
                "non-manufacturing", "multi-divisional", "controllable",
                "high-priority", "market-driven", "market-driving", "ingenious",
                "business-for-business", "inspirational", "winning", "boundaryless",
                "reality-based", "customer-focused", "preemptive", "location-specific",
                "revealing", "inventory-planning", "ubiquitous", "number-one",
                "results-oriented", "socially enabled", "well-scoped", "insight-based",
                "high-impact", "technology-driven", "knowledge-based",
                "information-age", "technology-centered", "critical", "cognitive",
                "acculturated", "client-centric", "comprehensive", "ground-breaking",
                "long-standing", "accelerating", "forward-thinking", "mind-blowing",
                "jaw-dropping", "transformative", "better-than-planned", "vital",
                "radical", "expanding", "fierce", "single-minded", "mindful",
                "top-down", "hands-on", "one-on-one", "analytic", "top", "elite",
                "dedicated", "curated", "highly-curated", "re-imagined",
                "thought-provoking", "quality-oriented", "task-oriented",
                "teamwork-oriented", "high-growth", "next-gen", "next-generation",
                "new-generation", "best-in-class", "best-of-class", "first-class",
                "top-class", "superior-quality", "synergistic", "micro-macro",
                "organization-wide", "clear-cut", "data-driven", "evidence-based",
                "transformational", "fast-paced", "real-time", "pre-approved",
                "unconventional", "advanced-analytics", "insight-driven",
                "sprint-based", "digitized", "hypothesis-driven", "governance-related",
                "convergent", "leadership-defined", "operations-oriented",
                "long-range", "dimensional", "award-winning", "user-centric",
                "first-to-market", "first-mover", "cross-platform", "on-the-go",
                "all-encompassing", "matrixed", "growth-enabling", "skills-based",
                "bottom-line", "top-shelf", "insourced", "out-of-the-box", "engaging",
                "on- and offline", "goals-based", "enriching", "medium-to-long-term",
                "adequate", "awareness-raising", "compatible", "supportive",
                "inspired", "high-return", "turn-key", "turnkey", "decision-ready",
                "diversified", "demanding", "ambitious", "domain-relevant", "novel",
                "pre-planned", "well-respected", "market-based", "distributor-based",
                "area-wide", "movements-based", "ever-changing", "purpose-driven",
                "resourceful", "real-life", "vibrant", "bright", "pure-play",
                "bespoke", "pivotal", "efficiency-enhancing", "multi-level", "rich",
                "frictionless", "up-to-the-minute", "sourced", "outcome-driven",
                "hyperaware", "high-velocity", "lean", "unmatched", "industry-leading",
                "multi-sided", "tailor-made", "contingent", "tangent",
                "moment-centric", "real-world", "inclusive", "efficiency-enabling",
                "value-creating", "alternative", "fit-for-purpose", "fast-changing",
                "onboarded", "active", "container packaged", "dynamically managed",
                "microservices-oriented", "higher-quality", "brute-force",
                "enterprise-sales-driven", "developer-led", "fast-track",
                "highly differentiated", "quick-to-deploy", "efficiency-focused",
                "as-a-service", "cloud-based", "activity-centric", "data-centric",
                "activity-focused", "data-focused", "workforce-focused",
                "organization-focused", "spot-on", "distributed", "deterministic",
                "converged", "on-premise", "company-first", "multi-vendor",
                "contextual", "hybrid", "higher-level", "user-driven", "full-stack",
                "build-as-you-go", "fully-digital", "agent-based", "AI-ready",
                "managerial", "industry-recognized", "top-ranking"
        ));
    }

    public static String eventualAdverb() {
        if (rand.nextInt(4) == 1) {
            return randomChoice(List.of(
                    "interactively", "credibly", "quickly", "proactively", "200%",
                    "24/7", "globally", "culturally", "technically", "strategically",
                    "swiftly", "cautiously", "expediently", "organically",
                    "carefully", "significantly", "conservatively", "adequately",
                    "genuinely", "efficiently", "seamlessly", "consistently",
                    "diligently", "dramatically", "straightforwardly",
                    "differentially", "gradually", "aggressively", "cost-effectively",
                    "proactively", "inherently", "directionally"
            )) + " ";
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
        return randomChoice(List.of(
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
        ));
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

    public static String personVerbHavingThingComplement(boolean plural) {
        String inner = randomChoice(List.of(
                "streamline", "interact with", "boost", "generate", "impact",
                "enhance", "leverage", "synergize", "generate", "empower", "enable",
                "prioritize", "transfer", "drive", "result in", "promote",
                "influence", "facilitate", "aggregate", "architect", "cultivate",
                "engage", "structure", "standardize", "accelerate", "deepen",
                "strengthen", "enforce", "foster", "turbocharge", "granularize",
                "operationalize", "reconceptualize", "iterate", "revolutionise",
                "digitize", "solutionize", "lead to", "reenergize", "restructure",
                "cross-pollinate", "ignite", "transgenerate"
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
        String inner = randomChoice(List.of(
                "streamline", "interact with", "boost", "generate", "impact",
                "enhance", "leverage", "synergize", "generate", "empower", "enable",
                "prioritize", "transfer", "drive", "result in", "promote",
                "influence", "facilitate", "aggregate", "architect", "cultivate",
                "engage", "structure", "standardize", "accelerate", "deepen",
                "strengthen", "enforce", "foster", "turbocharge", "granularize",
                "operationalize", "reconceptualize", "iterate", "revolutionise",
                "digitize", "solutionize", "lead to", "reenergize", "restructure",
                "cross-pollinate", "ignite", "transgenerate"
        ));
        return buildPluralVerb(inner, plural);
    }

    public static String thingVerbAndEnding(boolean plural) {
        boolean compl_sp = rand.nextBoolean();
        int r = rand.nextInt(103);
        if (r < 55) {
            return (thingVerbHavingThingComplement(plural) + " " +
                    thingWithRandomArticle(compl_sp));
        } else if (r < 100) {
            return (thingVerbHavingThingComplement(plural) + " the " +
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
            return randomChoice(List.of(
                    "we need to", "we've got to", "the reporting unit should",
                    "controlling should", "pursuing this route will enable us to",
                    "we will go the extra mile to", "we are working hard to",
                    "we continue to work tirelessly and diligently to",
                    "we will execute to", "we will sharpen our business models to",
                    "to continue our growth, we must", "we are going to",
                    "we look forward to working together to",
                    "in order to improve, you need to", "trending your numbers should"
            ));
        }
        return "we must activate the " + matrixOrSO() + " to";
    }

    public static String eventualPostfixedAdverb() {
        boolean plural = rand.nextBoolean();
        int r = rand.nextInt(255);
        if (r <= 38) {
            return randomChoice(List.of(
                    " going forward", " within the industry", " across the board",
                    " in this space", " from the get-go", " at the end of the day",
                    " throughout the organization", " as part of the plan",
                    " by thinking outside of the box", " ahead of schedule",
                    ", relative to our peers", " on a transitional basis",
                    " by expanding boundaries", " by nurturing talent",
                    ", as a Tier 1 company", " up-front", " on-the-fly",
                    " across our portfolio", " 50/50", " in the marketplace",
                    " by thinking and acting beyond boundaries",
                    " at the individual, team and organizational level",
                    " over the long term", " across geographies", " in the core",
                    " across industry sectors", " across the wider Group",
                    " by levelling the playing field", " on a day-to-day basis",
                    " across boundaries", " within the community",
                    " from within the data", " round-the-clock", " moving forward",
                    " downstream", " down the chain", " in the space",
                    " across the entire spectrum"
            ));
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
                randomChoice(List.of(
                        "streamline the process", "address the overarching issues",
                        "benchmark the portfolio", "manage the cycle",
                        "figure out where we come from, where we are going to",
                        "maximize the value", "execute the strategy",
                        "think out of the box", "think differently", "manage the balance",
                        "loop back", "conversate", "go forward together",
                        "achieve efficiencies", "deliver", "stay in the mix",
                        "stay in the zone", "evolve", "exceed expectations",
                        "develop the plan", "develop the blue print for execution",
                        "grow and diversify", "fuel changes", "nurture talent",
                        "turn every stone", "challenge established ideas",
                        "manage the portfolio", "align resources",
                        "drive the business forward", "make things happen", "stay ahead",
                        "outperform peers", "surge ahead", "manage the downside",
                        "stay in the wings", "come to a landing", "shoot it over",
                        "move the needle", "connect the dots",
                        "connect the dots to the end game", "reset the benchmark",
                        "take it offline", "peel the onion", "drill down",
                        "get from here to here", "do things differently",
                        "stretch the status quo", "challenge the status quo",
                        "challenge established ideas", "increase customer satisfaction",
                        "enable customer interaction", "manage the balance",
                        "turn every stone", "drive revenue", "rise to the challenge",
                        "keep it on the radar", "stay on trend", "hunt the business down",
                        "push the envelope to the tilt", "execute on priorities",
                        "stand out from the crowd", "make the abstract concrete",
                        "manage the mix", "grow", "accelerate the strategy",
                        "enhance the strength", "create long-term value",
                        "meet the challenges", "move the progress forward",
                        "do the right projects", "do the projects right",
                        "do more with less", "build winning teams",
                        "deliver on commitments", "execute", "deliver",
                        "see around the corner", "meet the surge", "celebrate the success",
                        "circle back", "action forward", "move forward", "take control",
                        "be cautiously optimistic", "be committed", "evolve our culture",
                        "leverage the benefits of our differentiation",
                        "stretch our data bucket", "leapfrog the competition",
                        "take the elevator beyond the top floor", "stick to the knitting",
                        "bring our vision to reality", "seize opportunities",
                        "create momentum", "generate company momentum",
                        "pursue new opportunities", "increase adherence",
                        "focus on the right things", "open the kimono", "give 110%",
                        "take it to the next level", "boil the ocean", "close the loop",
                        "create value", "disrupt the status quo", "be on the same page",
                        "deliver greater value for our customers",
                        "generate new value for shareholders",
                        "strengthen the balance sheet", "operate"
                ));
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
            return proposition()
                    ;
        } else if (r <= 280) {
            return proposition() + "; this is why " + proposition()
                    ;
        } else if (r <= 290) {
            return proposition() + "; nevertheless " + proposition()
                    ;
        } else if (r <= 300) {
            return proposition() + ", whereas " + proposition()
                    ;
        } else if (r <= 340) {
            return proposition() + ", while " + proposition()
                    ;
        } else if (r <= 350) {
            return proposition() + ". In the same time, " + proposition()
                    ;
        } else if (r <= 360) {
            return proposition() + ". As a result, " + proposition()
                    ;
        } else if (r <= 370) {
            return proposition() + ", whilst " + proposition()
                    ;
        } else if (r <= 373) {
            return "our gut-feeling is that " + proposition()
                    ;
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
            return "going forward, " + proposition()
                    ;
        } else if (r <= 389) {
            return "actually, " + proposition()
                    ;
        } else if (r <= 392) {
            return "in the future, " + proposition()
                    ;
        } else if (r <= 395) {
            return "flat out, " + proposition()
                    ;
        } else if (r <= 398) {
            return "first and foremost, " + proposition()
                    ;
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
            return "in today's fast-changing world, " + proposition()
                    ;
        } else if (r == 404) {
            return "internally and externally, " + proposition()
                    ;
        } else if (r == 405) {
            return "our message is: " + proposition()
                    ;
        } else if (r == 406) {
            return "in a data-first world, " + proposition()
                    ;
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