package ru.yalabuniversity.homework.lecture3.filesort;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class Sorter {

    public File sortFile(int fileSize, File dataFile) throws IOException {
        if(fileSize < 0) {
            throw new IOException("Вы введли отрицательное число");
        }
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        int middle = 0;
        int size = 2;
        int left = 0;
        int right = size - 1;
        while (size <= fileSize) {
            File targetFile = new File(".\\src\\main\\resources\\files\\outputData.txt");
            bufferedReader = new BufferedReader(new FileReader(dataFile));
            bufferedWriter = new BufferedWriter(new FileWriter(targetFile));
            while (right <= fileSize) {
                int k = 0;
                long[] partArr = new long[size];
                while (k < size) {
                    partArr[k] = Long.parseLong(bufferedReader.readLine());
                    k++;
                }
                long[] arrayA = new long[size / 2];
                System.arraycopy(partArr, 0, arrayA, 0, size / 2);
                Arrays.sort(arrayA);
                long[] arrayB = new long[size - size / 2];
                System.arraycopy(partArr, size / 2, arrayB, 0, size - size / 2);
                Arrays.sort(arrayB);
                long[] resultArr = mergeArray(arrayA, arrayB);
                int r = 0;
                while (r < resultArr.length) {
                    bufferedWriter.write(resultArr[r] + "\n");
                    r++;
                }
                bufferedWriter.flush();
                left += size;
                right = left + size;
            }
            if (right > fileSize) {
                right -= size;
                middle = right;
                while (right < fileSize) {
                    bufferedWriter.write(bufferedReader.readLine() + "\n");
                    right++;
                }
                bufferedWriter.flush();
            }
            size *= 2;
            bufferedReader.close();
            bufferedWriter.close();
            Files.copy(Paths.get(targetFile.toURI()), Paths.get(dataFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
            right = size - 1;
            left = 0;
            if (!targetFile.delete()) {
                throw new IOException("Возникла ошибка при удалении");
            }
        }
        if (middle != fileSize) {
            File targetFile = new File(".\\src\\main\\resources\\files\\outputData.txt");
            bufferedReader = new BufferedReader(new FileReader(dataFile));
            bufferedWriter = new BufferedWriter(new FileWriter(targetFile));
            int k = 0;
            long[] partArr = new long[fileSize];
            while (k < fileSize) {
                partArr[k] = Long.parseLong(bufferedReader.readLine());
                k++;
            }
            long[] arrayA = new long[middle];
            System.arraycopy(partArr, 0, arrayA, 0, middle);
            long[] arrayB = new long[fileSize - middle];
            System.arraycopy(partArr, middle, arrayB, 0, fileSize - middle);
            Arrays.sort(arrayB);
            long[] resultArr = mergeArray(arrayA, arrayB);
            int r = 0;
            while (r < resultArr.length) {
                bufferedWriter.write(resultArr[r] + "\n");
                r++;
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
            Files.copy(Paths.get(targetFile.toURI()), Paths.get(dataFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
            if (!targetFile.delete()) {
                throw new IOException("Возникла ошибка при удалении");
            }
        }
        bufferedReader.close();
        bufferedWriter.close();
        return dataFile;
    }

    private long[] mergeArray(long[] arrayA, long[] arrayB) {

        long[] result = new long[arrayA.length + arrayB.length];
        int positionA = 0, positionB = 0;

        for (int i = 0; i < result.length; i++) {
            if (positionA == arrayA.length) {
                result[i] = arrayB[positionB];
                positionB++;
            } else if (positionB == arrayB.length) {
                result[i] = arrayA[positionA];
                positionA++;
            } else if (arrayA[positionA] < arrayB[positionB]) {
                result[i] = arrayA[positionA];
                positionA++;
            } else {
                result[i] = arrayB[positionB];
                positionB++;
            }
        }
        return result;
    }
}