package com.acme.cbsg;

import java.util.Arrays;

import static com.acme.cbsg.CbsgDictionary.DEFAULT_DICTIONARY_FILE;


public final class CbsgGenerator {

    public static final String WORKSHOP = "--workshop";
    public static final String SHORT_WORKSHOP = "--shortWorkshop";
    public static final String FINANCIAL_REPORT = "--financialReport";
    public static final String SENTENCE_GUARANTEED_AMOUNT = "--sentenceGuaranteedAmount=";
    public static final String DICTIONARY_FILE = "--dictionaryFile=";
    public static final String HELP = "--help";

    public static final String HELP_TEXT = "Available options:\n"
            + WORKSHOP + "\n"
            + SHORT_WORKSHOP + "\n"
            + FINANCIAL_REPORT + "\n"
            + SENTENCE_GUARANTEED_AMOUNT + "<ANY_INTEGER>\n\n"
            + DICTIONARY_FILE + "<DICTIONARY_FILE>\n"
            + HELP + "\n";

    private static final CbsgDictionary CBSG_DICTIONARY = new CbsgDictionary();

    public static void main(String... args) {
        // Corporate Bullshit Generator
        CbsgDictionary cbsgDictionary = loadCbsgDictionary(args);
        Cbsg cbsg = new CbsgCore(cbsgDictionary);
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

    private static CbsgDictionary loadCbsgDictionary(String[] args) {
        String dictionaryFile = Arrays.stream(args)
                .filter(a -> a.startsWith(DICTIONARY_FILE))
                .map(s -> s.substring(DICTIONARY_FILE.length()))
                .findFirst()
                .orElse(DEFAULT_DICTIONARY_FILE);

        return new CbsgDictionary(dictionaryFile);
    }

}
