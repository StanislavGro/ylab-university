package ru.yalabuniversity.homework.lecture3.filesort.sort;

import java.io.*;

/*
    Идея следующая
    Имеем последовательность чисел (для наглядности она будет записана в строку, а не в столбец):
    5, 8, 3, 7, 1
    По данному алгоритму, на начальном этапе данная последовательность
    представляет собой следующую последовательность чанков (отсортированных кусков данных):
    dataF: [5], [8], [3], [7], [1]
    Делим данную последовательность на 2 ленты c размером чанка 1:
    tape 1: [5], [3], [1]
    tape 2: [8], [7]
    Собриаем из 2-х лент одну, в которой сортируем и собираем чанки размера 1, остатки в конец:
    dataF: [5, 8], [3, 7], [1]
    Снова делим данную последовательность на 2 ленты c размером чанка * 2, то есть 2:
    tape 1: [5, 8], [1]
    tape 2: [3, 7]
    Снова собриаем из 2-х лент одну, в которой сортируем и собираем чанки размера * 2, то есть 2:
    dataF: [3, 5, 7, 8], [1]
    Делим данную последовательность на 2 ленты c размером чанка * 2, то есть 4, 1 просто идет во вторую ленту:
    tape 1: [3, 5, 7, 8]
    tape 2: [1]
    Окончательно собриаем из 2-х лент одну, в которой сортируем чанки размера * 2, то есть 4:
    dataF: [1, 3, 5, 7, 8]
    Сортировка получения из 2-х лент одну основана на методе 2-х указателей или сортировке слиянием
    Алгоритм значительно замедляется из-за такого разделения по методам, но это гораздо более читаемо и понятно!
*/
public class Sorter {
    public File sortFile(File dataFile, int fileSize) throws IOException {
        Tape tapeA = new Tape(".\\src\\main\\resources\\files\\tapeA.txt");
        Tape tapeB = new Tape(".\\src\\main\\resources\\files\\tapeB.txt");
        int chunkSize = 1;
        while (chunkSize < fileSize) {
            // Формируем 2 ленты с чанками равными chunkSize
            fillTapes(dataFile, tapeA, tapeB, chunkSize);
            // Чистим старый dataFile, чтобы запонить его частично отсоритрованными чанками
            refreshDataFile(dataFile);
            // Реализуем сортировку слиянием основанную на 2-х указателях
            mergeSort(dataFile, tapeA, tapeB, chunkSize);
            // Чистим и обновляем уже ненужные ленты
            tapeA.refreshFile();
            tapeB.refreshFile();
            // Увеличиваем чанк в 2 раза
            chunkSize *= 2;
        }
        // Удаляем файлы
        tapeA.deleteFile();
        tapeB.deleteFile();
        return dataFile;
    }

    //Инициализвация затратна, но я это сделал чтобы код был чище, прощу прощенья
    private void fillTapes(File dataFile, Tape tapeA, Tape tapeB, int chunkSize) throws IOException {
        try (BufferedReader dataFileBufferedReader = new BufferedReader(new FileReader(dataFile));
             BufferedWriter tapeABufferedWriter = new BufferedWriter(new FileWriter(tapeA.getChunkFile()));
             BufferedWriter tapeBBufferedWriter = new BufferedWriter(new FileWriter(tapeB.getChunkFile()))) {
            String line;
            int i = 0;
            // Флаг, нужный чтобы записывать в файл последовательность чанка сначала из tapeA, а потом из tapeB
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

            // Сливаем все в один файл сортируя чанки методом двух указателей
            String lineA = tapeABufferedReader.readLine();
            String lineB = tapeBBufferedReader.readLine();
            long numberA;
            long numberB;
            // Пока не дойдем до конца одного из файлов
            // Тут идет сортировка целых чанков
            while (lineA != null && lineB != null) {
                int chunkA = 0;
                int chunkB = 0;
                // А тут сортировка внутренностей чанков между собой
                while (lineA != null && lineB != null && chunkA < chunkSize && chunkB < chunkSize) {
                    // Конвертация
                    numberA = Long.parseLong(lineA);
                    numberB = Long.parseLong(lineB);
                    // Сравнение и запись в dataFile
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
                // Цикл будет выполняться только если чанк из A в конце концов окажется не пустым
                // Мы знаем что чанки внутри отсортированные, поэтому можем смело записывать в последовательность
                while (lineA != null && chunkA < chunkSize) {
                    numberA = Long.parseLong(lineA);
                    dataFileBufferedWriter.write(numberA + "\n");
                    dataFileBufferedWriter.flush();
                    lineA = tapeABufferedReader.readLine();
                    chunkA++;
                }
                // Аналогично но для tapeB
                while (lineB != null && chunkB < chunkSize) {
                    numberB = Long.parseLong(lineB);
                    dataFileBufferedWriter.write(numberB + "\n");
                    dataFileBufferedWriter.flush();
                    lineB = tapeBBufferedReader.readLine();
                    chunkB++;
                }
            }
            // Если такслучится, что останется один единственный чанк из tapeA для которого нет соседа из B для сортировки
            // То мы просто его полностью запишем в конец, так как он и так отсортирован
            while (lineA != null) {
                numberA = Long.parseLong(lineA);
                dataFileBufferedWriter.write(numberA + "\n");
                dataFileBufferedWriter.flush();
                lineA = tapeABufferedReader.readLine();
            }
            // Аналогично для tapeB
            while (lineB != null) {
                numberB = Long.parseLong(lineB);
                dataFileBufferedWriter.write(numberB + "\n");
                dataFileBufferedWriter.flush();
                lineB = tapeBBufferedReader.readLine();
            }
        }
    }
}
