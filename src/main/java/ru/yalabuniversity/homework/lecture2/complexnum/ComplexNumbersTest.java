package ru.yalabuniversity.homework.lecture2.complexnum;

public class ComplexNumbersTest {
    public static void main(String[] args) {
        ComplexNumbers complexNumbers1 = new ComplexNumbersImpl(1, 3);
        ComplexNumbers complexNumbers2 = new ComplexNumbersImpl(4, -5);
        System.out.println(complexNumbers1);
        System.out.println(complexNumbers2);
        System.out.println(complexNumbers1.sum(complexNumbers2));
        System.out.println(complexNumbers1.divide(complexNumbers2));

        ComplexNumbers complexNumbers3 = new ComplexNumbersImpl(1, -1);
        ComplexNumbers complexNumbers4 = new ComplexNumbersImpl(3, 6);
        System.out.println(complexNumbers3);
        System.out.println(complexNumbers4);
        System.out.println(complexNumbers3.multiply(complexNumbers4));
        System.out.println(complexNumbers3.module());
    }
}
