package ru.yalabuniversity.homework.lecture3.filesort;

import java.io.*;

public class SorterNew {

    public File sortFile(File dataFile) throws IOException {
        Tape tapeA = new Tape(".\\src\\main\\resources\\files\\tapeA.txt");
        Tape tapeB = new Tape(".\\src\\main\\resources\\files\\tapeB.txt");

        int serial = 1;
        int fileSize = 0;
        do {
            BufferedReader dataFileBufferedReader = new BufferedReader(new FileReader(dataFile));
            BufferedReader tapeABufferedReader = new BufferedReader(new FileReader(tapeA.getFile()));
            BufferedReader tapeBBufferedReader = new BufferedReader(new FileReader(tapeB.getFile()));
            BufferedWriter tapeABufferedWriter = new BufferedWriter(new FileWriter(tapeA.getFile()));
            BufferedWriter tapeBBufferedWriter = new BufferedWriter(new FileWriter(tapeB.getFile()));

            // ‘ормируем 2 ленты с чанками сначала равными 1, потом 2, потом 4, потом 8...
            String line;
            int i = 0;
            boolean isFirst = true;
            while ((line = dataFileBufferedReader.readLine()) != null) {
                if (isFirst) {
                    tapeABufferedWriter.write(line + "\n");
                    tapeABufferedWriter.flush();
                    i++;
                    if (i == serial) {
                        isFirst = false;
                        i = 0;
                    }
                } else {
                    tapeBBufferedWriter.write(line + "\n");
                    tapeBBufferedWriter.flush();
                    i++;
                    if (i == serial) {
                        isFirst = true;
                        i = 0;
                    }
                }
                fileSize++;
            }

            dataFileBufferedReader.close();
            dataFile.delete();
            dataFile = new File(".\\src\\main\\resources\\files\\data.txt");
            dataFile.createNewFile();
            BufferedWriter dataFileBufferedWriter = new BufferedWriter(new FileWriter(dataFile));

            // —ливаем все в один файл сортиру€ чанки методом двух указателей
            String lineA = tapeABufferedReader.readLine();
            String lineB = tapeBBufferedReader.readLine();
            i = 0;
            while (lineA != null && lineB != null) {
                long numberA = Long.parseLong(lineA);
                long numberB = Long.parseLong(lineB);
                if (numberA > numberB) {
                    dataFileBufferedWriter.write(numberB + "\n");
                    dataFileBufferedWriter.write(numberA + "\n");
                } else {
                    dataFileBufferedWriter.write(numberA + "\n");
                    dataFileBufferedWriter.write(numberB + "\n");
                }
                dataFileBufferedWriter.flush();
                lineA = tapeABufferedReader.readLine();
                lineB = tapeBBufferedReader.readLine();
            }

            while (lineA != null) {
                long numberA = Long.parseLong(lineA);
                dataFileBufferedWriter.write(numberA + "\n");
                lineA = tapeBBufferedReader.readLine();
            }
            while (lineB != null) {
                long numberB = Long.parseLong(lineB);
                dataFileBufferedWriter.write(numberB + "\n");
                lineB = tapeBBufferedReader.readLine();
            }

            dataFileBufferedWriter.close();
            dataFileBufferedReader.close();
            tapeABufferedWriter.close();
            tapeABufferedReader.close();
            tapeBBufferedWriter.close();
            tapeBBufferedReader.close();

            tapeA.refreshFile();
            tapeB.refreshFile();
            serial *= 2;
        } while (serial < fileSize);
        return dataFile;
    }
}
