package ru.yalabuniversity.homework.lecture3.transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        String res = transliterator
                .transliterate("HELLO! ÏĞÈÂÅÒ! Go, boy!");
        System.out.println(res);
    }
}
