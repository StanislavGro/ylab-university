package ru.yalabuniversity.homework.lecture2.ratelimitedprinter;

import java.io.IOException;

public class RateLimitedPrinterTest {
    public static void main(String[] args) {
        try {
            RateLimitedPrinter rateLimitedPrinter = new RateLimitedPrinter(1000);
            for (int i = 0; i < 1_000_000_000; i++)
                rateLimitedPrinter.print(i + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
