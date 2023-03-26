package ru.yalabuniversity.homework.lecture3.filesort.sort;

import java.io.File;
import java.io.IOException;

/**
 * Класс Tape представляет собой АТД Лента,
 * имеющий под капотом ссылку на файл,
 * в котором хранятся чанки (chunk) -
 * наборы отсортированных кусочков данных, идущих
 * последовательно как в обычных списках или массивах
 * но их размер выходит за пределы оперативной памяти
 */
public class Tape {
    private File chunkFile;
    private String chunkFileDir;

    /**
     * Конструктор позволяющий создать объект класса Tape
     * @param chunkFileDir путь к файлу в виде строки
     * @throws IOException ошибка, которая может возникуть при создании файла
     */
    public Tape(String chunkFileDir) throws IOException {
        this.chunkFileDir = chunkFileDir;
        this.chunkFile = new File(chunkFileDir);
        chunkFile.createNewFile();
    }

    /**
     * Метод возвращает файл содержащий текущие отсортированные чанки
     * @return возвращает файл с чанками
     */
    public File getChunkFile() {
        return chunkFile;
    }

    /**
     * Метод, который затирает старый файл с чанками
     * и создает на базе уже имеющегося chunkFileDir
     * чистый пустой файл.
     * @throws IOException ошибка, которая может возникуть при создании файла
     */
    public void refreshFile() throws IOException {
        chunkFile.delete();
        chunkFile = new File(chunkFileDir);
        chunkFile.createNewFile();
    }

    /**
     * Удаляет файл, в котором хранит элементы
     */
    public void deleteFile() {
        this.chunkFile.delete();
    }
}