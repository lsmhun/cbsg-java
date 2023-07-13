package com.acme.cbsg;

import java.util.Arrays;
import java.util.Properties;

import static com.acme.cbsg.CbsgResourceUtil.DEFAULT_DICTIONARY_PROPERIES;

public class CbsgGenerator {

    public static final String WORKSHOP = "--workshop";
    public static final String SHORT_WORKSHOP = "--shortWorkshop";
    public static final String FINANCIAL_REPORT = "--financialReport";
    public static final String SENTENCE_GUARANTEED_AMOUNT = "--sentenceGuaranteedAmount=";
    public static final String CONFIGURATION_PROPERTIES = "--configurationProperties=";
    public static final String HELP = "--help";

    public static final String HELP_TEXT = "Available options:\n"
            + WORKSHOP + "\n"
            + SHORT_WORKSHOP + "\n"
            + FINANCIAL_REPORT + "\n"
            + SENTENCE_GUARANTEED_AMOUNT + "<ANY_INTEGER>\n\n"
            + CONFIGURATION_PROPERTIES + "<DICTIONARY_PROPERTIES_FROM_CLASSPATH>\n"
            + HELP + "\n";

    private static final CbsgResourceUtil cbsgResourceUtil = new CbsgResourceUtil();

    public static void main(String... args) {
        // Corporate Bullshit Generator
        Properties cbsgConfiguration = loadCbsgConfiguration(args);
        Cbsg cbsg = new CbsgCore(cbsgConfiguration);
        String text = getCbsText(cbsg, args);
        System.out.println(text);
    }

    private static String getCbsText(Cbsg cbsg, String[] args) {
        String text = "";
        for (String arg: args) {
            if(arg.startsWith(SENTENCE_GUARANTEED_AMOUNT)){
                text = cbsg.sentenceGuaranteedAmount(Integer.parseInt(arg.substring(SENTENCE_GUARANTEED_AMOUNT.length())));
                break;
            }
            switch (arg){
                case WORKSHOP: text = cbsg.workshop();break;
                case SHORT_WORKSHOP: text = cbsg.shortWorkshop();break;
                case FINANCIAL_REPORT: text = cbsg.financialReport();break;
                case HELP: text = HELP_TEXT;break;
            }
        }
        if("".equals(text)){
            return cbsg.shortWorkshop();
        }
        return text;
    }

    private static Properties loadCbsgConfiguration(String[] args) {
        String configurationProperties = Arrays.stream(args)
                .filter(a -> a.startsWith(CONFIGURATION_PROPERTIES))
                .map(s -> s.substring(SENTENCE_GUARANTEED_AMOUNT.length()))
                .findFirst()
                .orElse(DEFAULT_DICTIONARY_PROPERIES);

        return cbsgResourceUtil.readProperties(configurationProperties);
    }

}
