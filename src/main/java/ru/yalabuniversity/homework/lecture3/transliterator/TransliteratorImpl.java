package ru.yalabuniversity.homework.lecture3.transliterator;

import java.util.HashMap;
import java.util.Map;

public class TransliteratorImpl implements Transliterator {
    private static final Map<Character, String> translitMap;

    static {
        translitMap = new HashMap<>();
        translitMap.put('А', "A");
        translitMap.put('В', "V");
        translitMap.put('Б', "B");
        translitMap.put('Г', "G");
        translitMap.put('Д', "D");
        translitMap.put('Е', "E");
        translitMap.put('Ё', "E");
        translitMap.put('Ж', "ZH");
        translitMap.put('З', "Z");
        translitMap.put('И', "I");
        translitMap.put('Й', "I");
        translitMap.put('К', "K");
        translitMap.put('Л', "L");
        translitMap.put('М', "M");
        translitMap.put('Н', "N");
        translitMap.put('О', "O");
        translitMap.put('П', "P");
        translitMap.put('Р', "R");
        translitMap.put('С', "S");
        translitMap.put('Т', "T");
        translitMap.put('У', "U");
        translitMap.put('Ф', "F");
        translitMap.put('Х', "KH");
        translitMap.put('Ц', "TS");
        translitMap.put('Ч', "CH");
        translitMap.put('Ш', "SH");
        translitMap.put('Щ', "SHCH");
        translitMap.put('Ъ', "IE");
        translitMap.put('Ы', "Y");
        translitMap.put('Ь', "");
        translitMap.put('Э', "E");
        translitMap.put('Ю', "IU");
        translitMap.put('Я', "IA");
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