package ru.yalabuniversity.homework.lecture3.transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        test("HELLO! ÏĞÈÂÅÒ! Go, boy!");
        test("");
        test("My name is ÑÒÀÍÈÑËÀÂ ÃĞÎÕÎÒÎÂ");
        test("ÍÓ À ÒÓÒ ÓÆÅ ÂÑÅ ÍÀ ÀÍÃËÈÉÑÊÎÌ");
    }

    private static void test(String str) {
        Transliterator transliterator = new TransliteratorImpl();
        String res = transliterator.transliterate(str);
        System.out.println(res);
    }
}
