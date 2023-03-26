package ru.yalabuniversity.homework.lecture3.transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        test("HELLO! ПРИВЕТ! Go, boy!");
        test("");
        test("My name is СТАНИСЛАВ ГРОХОТОВ");
        test("НУ А ТУТ УЖЕ ВСЕ НА АНГЛИЙСКОМ");
    }

    private static void test(String str) {
        Transliterator transliterator = new TransliteratorImpl();
        String res = transliterator.transliterate(str);
        System.out.println(res);
    }
}