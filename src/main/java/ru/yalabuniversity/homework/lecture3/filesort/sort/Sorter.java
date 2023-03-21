package ru.yalabuniversity.homework.lecture3.filesort.sort;

import java.io.*;

/*
    ���� ���������
    ����� ������������������ ����� (��� ����������� ��� ����� �������� � ������, � �� � �������):
    5, 8, 3, 7, 1
    �� ������� ���������, �� ��������� ����� ������ ������������������
    ������������ ����� ��������� ������������������ ������ (��������������� ������ ������):
    dataF: [5], [8], [3], [7], [1]
    ����� ������ ������������������ �� 2 ����� c �������� ����� 1:
    tape 1: [5], [3], [1]
    tape 2: [8], [7]
    �������� �� 2-� ���� ����, � ������� ��������� � �������� ����� ������� 1, ������� � �����:
    dataF: [5, 8], [3, 7], [1]
    ����� ����� ������ ������������������ �� 2 ����� c �������� ����� * 2, �� ���� 2:
    tape 1: [5, 8], [1]
    tape 2: [3, 7]
    ����� �������� �� 2-� ���� ����, � ������� ��������� � �������� ����� ������� * 2, �� ���� 2:
    dataF: [3, 5, 7, 8], [1]
    ����� ������ ������������������ �� 2 ����� c �������� ����� * 2, �� ���� 4, 1 ������ ���� �� ������ �����:
    tape 1: [3, 5, 7, 8]
    tape 2: [1]
    ������������ �������� �� 2-� ���� ����, � ������� ��������� ����� ������� * 2, �� ���� 4:
    dataF: [1, 3, 5, 7, 8]
    ���������� ��������� �� 2-� ���� ���� �������� �� ������ 2-� ���������� ��� ���������� ��������
    �������� ����������� ����������� ��-�� ������ ���������� �� �������, �� ��� ������� ����� ������� � �������!
*/
public class Sorter {
    public File sortFile(File dataFile, int fileSize) throws IOException {
        Tape tapeA = new Tape(".\\src\\main\\resources\\files\\tapeA.txt");
        Tape tapeB = new Tape(".\\src\\main\\resources\\files\\tapeB.txt");
        int chunkSize = 1;
        while (chunkSize < fileSize) {
            // ��������� 2 ����� � ������� ������� chunkSize
            fillTapes(dataFile, tapeA, tapeB, chunkSize);
            // ������ ������ dataFile, ����� �������� ��� �������� ���������������� �������
            refreshDataFile(dataFile);
            // ��������� ���������� �������� ���������� �� 2-� ����������
            mergeSort(dataFile, tapeA, tapeB, chunkSize);
            // ������ � ��������� ��� �������� �����
            tapeA.refreshFile();
            tapeB.refreshFile();
            // ����������� ���� � 2 ����
            chunkSize *= 2;
        }
        // ������� �����
        tapeA.deleteFile();
        tapeB.deleteFile();
        return dataFile;
    }

    //�������������� ��������, �� � ��� ������ ����� ��� ��� ����, ����� ��������
    private void fillTapes(File dataFile, Tape tapeA, Tape tapeB, int chunkSize) throws IOException {
        try (BufferedReader dataFileBufferedReader = new BufferedReader(new FileReader(dataFile));
             BufferedWriter tapeABufferedWriter = new BufferedWriter(new FileWriter(tapeA.getChunkFile()));
             BufferedWriter tapeBBufferedWriter = new BufferedWriter(new FileWriter(tapeB.getChunkFile()))) {
            String line;
            int i = 0;
            // ����, ������ ����� ���������� � ���� ������������������ ����� ������� �� tapeA, � ����� �� tapeB
            boolean isFirst = true;
            while ((line = dataFileBufferedReader.readLine()) != null) {
                if (isFirst) {
                    tapeABufferedWriter.write(line + "\n");
                    tapeABufferedWriter.flush();
                    i++;
                    if (i == chunkSize) {
                        isFirst = false;
                        i = 0;
                    }
                } else {
                    tapeBBufferedWriter.write(line + "\n");
                    tapeBBufferedWriter.flush();
                    i++;
                    if (i == chunkSize) {
                        isFirst = true;
                        i = 0;
                    }
                }
            }
        }
    }

    private void refreshDataFile(File dataFile) throws IOException {
        dataFile.delete();
        dataFile = new File(".\\src\\main\\resources\\files\\data.txt");
        dataFile.createNewFile();
    }

    private void mergeSort(File dataFile, Tape tapeA, Tape tapeB, int chunkSize) throws IOException {
        try (BufferedReader tapeABufferedReader = new BufferedReader(new FileReader(tapeA.getChunkFile()));
             BufferedReader tapeBBufferedReader = new BufferedReader(new FileReader(tapeB.getChunkFile()));
             BufferedWriter dataFileBufferedWriter = new BufferedWriter(new FileWriter(dataFile))) {

            // ������� ��� � ���� ���� �������� ����� ������� ���� ����������
            String lineA = tapeABufferedReader.readLine();
            String lineB = tapeBBufferedReader.readLine();
            long numberA;
            long numberB;
            // ���� �� ������ �� ����� ������ �� ������
            // ��� ���� ���������� ����� ������
            while (lineA != null && lineB != null) {
                int chunkA = 0;
                int chunkB = 0;
                // � ��� ���������� ������������� ������ ����� �����
                while (lineA != null && lineB != null && chunkA < chunkSize && chunkB < chunkSize) {
                    // �����������
                    numberA = Long.parseLong(lineA);
                    numberB = Long.parseLong(lineB);
                    // ��������� � ������ � dataFile
                    if (numberA > numberB) {
                        dataFileBufferedWriter.write(numberB + "\n");
                        chunkB++;
                        lineB = tapeBBufferedReader.readLine();
                    } else {
                        dataFileBufferedWriter.write(numberA + "\n");
                        chunkA++;
                        lineA = tapeABufferedReader.readLine();
                    }
                    dataFileBufferedWriter.flush();
                }
                // ���� ����� ����������� ������ ���� ���� �� A � ����� ������ �������� �� ������
                // �� ����� ��� ����� ������ ���������������, ������� ����� ����� ���������� � ������������������
                while (lineA != null && chunkA < chunkSize) {
                    numberA = Long.parseLong(lineA);
                    dataFileBufferedWriter.write(numberA + "\n");
                    dataFileBufferedWriter.flush();
                    lineA = tapeABufferedReader.readLine();
                    chunkA++;
                }
                // ���������� �� ��� tapeB
                while (lineB != null && chunkB < chunkSize) {
                    numberB = Long.parseLong(lineB);
                    dataFileBufferedWriter.write(numberB + "\n");
                    dataFileBufferedWriter.flush();
                    lineB = tapeBBufferedReader.readLine();
                    chunkB++;
                }
            }
            // ���� �����������, ��� ��������� ���� ������������ ���� �� tapeA ��� �������� ��� ������ �� B ��� ����������
            // �� �� ������ ��� ��������� ������� � �����, ��� ��� �� � ��� ������������
            while (lineA != null) {
                numberA = Long.parseLong(lineA);
                dataFileBufferedWriter.write(numberA + "\n");
                dataFileBufferedWriter.flush();
                lineA = tapeABufferedReader.readLine();
            }
            // ���������� ��� tapeB
            while (lineB != null) {
                numberB = Long.parseLong(lineB);
                dataFileBufferedWriter.write(numberB + "\n");
                dataFileBufferedWriter.flush();
                lineB = tapeBBufferedReader.readLine();
            }
        }
    }
}
