package ru.yalabuniversity.homework.lecture3.filesort;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        int count = 1_000_000;
        File dataFile = new Generator().generate(".\\src\\main\\resources\\files\\data.txt", count);
        System.out.println(new Validator(dataFile).isSorted()); // false
        long start = System.currentTimeMillis();
        File sortedFile = new Sorter(count).sortFile(dataFile);
        System.out.println("Время = " + (System.currentTimeMillis() - start));
        System.out.println(new Validator(sortedFile).isSorted()); // true
    }
}
