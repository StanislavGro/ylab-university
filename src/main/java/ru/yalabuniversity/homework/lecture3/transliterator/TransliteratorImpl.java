package ru.yalabuniversity.homework.lecture3.transliterator;

import java.util.HashMap;
import java.util.Map;

public class TransliteratorImpl implements Transliterator {
    private static final Map<Character, String> translitMap;

    static {
        translitMap = new HashMap<>();
        translitMap.put('À', "A");
        translitMap.put('Â', "V");
        translitMap.put('Á', "B");
        translitMap.put('Ã', "G");
        translitMap.put('Ä', "D");
        translitMap.put('Å', "E");
        translitMap.put('¨', "E");
        translitMap.put('Æ', "ZH");
        translitMap.put('Ç', "Z");
        translitMap.put('È', "I");
        translitMap.put('É', "I");
        translitMap.put('Ê', "K");
        translitMap.put('Ë', "L");
        translitMap.put('Ì', "M");
        translitMap.put('Í', "N");
        translitMap.put('Î', "O");
        translitMap.put('Ï', "P");
        translitMap.put('Ğ', "R");
        translitMap.put('Ñ', "S");
        translitMap.put('Ò', "T");
        translitMap.put('Ó', "U");
        translitMap.put('Ô', "F");
        translitMap.put('Õ', "KH");
        translitMap.put('Ö', "TS");
        translitMap.put('×', "CH");
        translitMap.put('Ø', "SH");
        translitMap.put('Ù', "SHCH");
        translitMap.put('Ú', "IE");
        translitMap.put('Û', "Y");
        translitMap.put('Ü', "");
        translitMap.put('İ', "E");
        translitMap.put('Ş', "IU");
        translitMap.put('ß', "IA");
    }

    @Override
    public String transliterate(String source) {
        StringBuilder result = new StringBuilder();
        for (char ch : source.toCharArray()) {
            if (translitMap.containsKey(ch)) {
                result.append(translitMap.get(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}
