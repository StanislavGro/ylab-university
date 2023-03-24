package ru.yalabuniversity.homework.lecture3.filesort;

import ru.yalabuniversity.homework.lecture3.filesort.sort.Sorter;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        int count = 100_001;
        File dataFile = new Generator().generate(".\\src\\main\\resources\\files\\data.txt", count);
        System.out.println(new Validator(dataFile).isSorted());
        long start = System.currentTimeMillis();
        // Немного изменил параметры метода, добавил в придачу к файлу количество элементов в нем. Думаю уловие не поменятся, а фокус не будет сбиваться на подсчет количества элементов
        File sortedFile = new Sorter().sortFile(dataFile, count);
        long end = System.currentTimeMillis();
        System.out.println("Время выполнения: " + (end - start) + " мс");
        System.out.println(new Validator(sortedFile).isSorted());
    }
}