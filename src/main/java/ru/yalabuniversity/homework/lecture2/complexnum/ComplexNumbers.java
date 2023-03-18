package ru.yalabuniversity.homework.lecture2.complexnum;

public interface ComplexNumbers {
    double getImage();
    double getReal();
    double module();
    // Паттерны chain
    ComplexNumbers sum(ComplexNumbers complexNumber);
    ComplexNumbers divide(ComplexNumbers complexNumber);
    ComplexNumbers multiply(ComplexNumbers complexNumber);
}
