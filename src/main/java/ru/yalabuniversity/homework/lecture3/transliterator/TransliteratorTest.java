package ru.yalabuniversity.homework.lecture3.transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        test("HELLO! ������! Go, boy!");
        test("");
        test("My name is ��������� ��������");
        test("�� � ��� ��� ��� �� ����������");
    }

    private static void test(String str) {
        Transliterator transliterator = new TransliteratorImpl();
        String res = transliterator.transliterate(str);
        System.out.println(res);
    }
}
