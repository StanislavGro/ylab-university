package ru.yalabuniversity.homework.lecture1;

import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int hiddenNumber = new Random().nextInt(99) + 1;
            int attempt = 0;
            int maxAttempts = 10;
            System.out.println("Я загадал число от 1 до 99. У тебя " + maxAttempts + " попыток угадать.");
            while (attempt++ < maxAttempts) {
                int input = scanner.nextInt();
                if (input == hiddenNumber)
                    System.out.println("Ты угадал с " + attempt + " попытки!");
                else if (input < hiddenNumber)
                    System.out.println("Мое число больше! У тебя осталось " + (maxAttempts - attempt) + " попыток");
                else
                    System.out.println("Мое число меньше! У тебя осталось " + (maxAttempts - attempt) + " попыток");
            }
            System.out.println("Ты не угадал");
        }
    }
}
