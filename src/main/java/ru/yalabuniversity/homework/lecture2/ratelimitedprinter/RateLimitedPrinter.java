package ru.yalabuniversity.homework.lecture2.ratelimitedprinter;

import java.io.IOException;

public class RateLimitedPrinter {
    private final long interval;
    // Данный вариант выводит первое сообщение всегда сразу, так как начальное значение 0
    // Подходит для того, чтобы наглядно увидеть работу в цикле с самого первого выводимого значения
    private long startTime;
//    Альтернативный вариант
//    Во время создания объекта отмечаем время, когда это произошло
//    Таким образом если после создания объекта прошло достаточно времени, то можно выводить сообщение
//    private long startTime = System.currentTimeMillis();

    public RateLimitedPrinter(long interval) throws IOException {
        if (interval < 0)
            throw new IOException("You entered a NEGATIVE interval");
        this.interval = interval;
    }

    public void print(String message) {
        long endTime = System.currentTimeMillis();
        if (endTime - startTime > interval) {
            System.out.println(message);
            startTime = endTime;
        }
    }
}
