package ru.yalabuniversity.homework.lecture3.filesort;

import java.io.File;
import java.io.IOException;

public class Tape {
    private File file;
    private String filePath;
    public Tape(String filePath) throws IOException {
        this.filePath = filePath;
        this.file = new File(filePath);
        file.createNewFile();
    }

    public File getFile() {
        return file;
    }

    public void refreshFile() throws IOException {
        file.delete();
        file = new File(filePath);
        file.createNewFile();
    }
}
