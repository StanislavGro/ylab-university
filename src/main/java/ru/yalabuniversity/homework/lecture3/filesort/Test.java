package ru.yalabuniversity.homework.lecture3.filesort;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
//        int count = 1_000_000;
//        File dataFile = new Generator().generate(".\\src\\main\\resources\\files\\data.txt", count);
//        System.out.println(new Validator(dataFile).isSorted());
//        long start = System.currentTimeMillis();
//        File sortedFile = new Sorter().sortFile(count, dataFile);
//        System.out.println("Время выполнения: " + (System.currentTimeMillis() - start) + " мс");
//        System.out.println(new Validator(sortedFile).isSorted());
        int count = 9;
        File dataFile = new Generator().generate(".\\src\\main\\resources\\files\\data.txt", count);
        System.out.println(new Validator(dataFile).isSorted());
        File sortedFile = new SorterNew().sortFile(dataFile);
    }
}
