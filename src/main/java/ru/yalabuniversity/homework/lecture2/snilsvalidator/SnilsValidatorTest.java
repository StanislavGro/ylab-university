package ru.yalabuniversity.homework.lecture2.snilsvalidator;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        SnilsValidator snilsValidator = new SnilsValidatorImpl();
        System.out.println(snilsValidator.validate("01468870570"));
        System.out.println(snilsValidator.validate("90114404441"));
        System.out.println(snilsValidator.validate("901144044413423"));
    }
}
