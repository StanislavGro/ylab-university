package ru.yalabuniversity.homework.lecture2.snilsvalidator;

public class SnilsValidatorImpl implements SnilsValidator {

    // Скорость O(n)
    // Занимаемое пространство O(1)
    @Override
    public boolean validate(String snils) {

        int len = snils.length();

        if(len != 11)
            return false;

        int sum = 0;

        for (int i = 0; i < 9; i++)
            sum += Character.digit(snils.charAt(i), 10) * (9 - i);

        int controlSum;

        if(sum < 100)
            controlSum = sum;
        else if (sum == 100)
            controlSum = 0;
        else {
            int temp = sum % 101;
            if(temp == 100)
                controlSum = 0;
            else
                controlSum = temp;
        }

        int lastTwoSymbols = Character.digit(snils.charAt(9), 10) * 10 + Character.digit(snils.charAt(10), 10);

        // Проверка на равенство двух чисел без тяжелых equals в стрингах
        return lastTwoSymbols == controlSum;
    }
}
