package ru.yalabuniversity.homework.lecture3.transliterator;

import java.util.HashMap;
import java.util.Map;

public class TransliteratorImpl implements Transliterator {
    private static final Map<Character, String> translitMap;

    static {
        translitMap = new HashMap<>();
        translitMap.put('�', "A");
        translitMap.put('�', "V");
        translitMap.put('�', "B");
        translitMap.put('�', "G");
        translitMap.put('�', "D");
        translitMap.put('�', "E");
        translitMap.put('�', "E");
        translitMap.put('�', "ZH");
        translitMap.put('�', "Z");
        translitMap.put('�', "I");
        translitMap.put('�', "I");
        translitMap.put('�', "K");
        translitMap.put('�', "L");
        translitMap.put('�', "M");
        translitMap.put('�', "N");
        translitMap.put('�', "O");
        translitMap.put('�', "P");
        translitMap.put('�', "R");
        translitMap.put('�', "S");
        translitMap.put('�', "T");
        translitMap.put('�', "U");
        translitMap.put('�', "F");
        translitMap.put('�', "KH");
        translitMap.put('�', "TS");
        translitMap.put('�', "CH");
        translitMap.put('�', "SH");
        translitMap.put('�', "SHCH");
        translitMap.put('�', "IE");
        translitMap.put('�', "Y");
        translitMap.put('�', "");
        translitMap.put('�', "E");
        translitMap.put('�', "IU");
        translitMap.put('�', "IA");
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
