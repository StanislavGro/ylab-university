package ru.yalabuniversity.homework.lecture3.filesort.sort;

import java.io.File;
import java.io.IOException;

/**
 * ����� Tape ������������ ����� ��� �����,
 * ������� ��� ������� ������ �� ����,
 * � ������� �������� ����� (chunk) -
 * ������ ��������������� �������� ������, ������
 * ��������������� ��� � ������� ������� ��� ��������
 * �� �� ������ ������� �� ������� ����������� ������
 */
public class Tape {
    private File chunkFile;
    private String chunkFileDir;

    /**
     * ����������� ����������� ������� ������ ������ Tape
     * @param chunkFileDir ���� � ����� � ���� ������
     * @throws IOException ������, ������� ����� ��������� ��� �������� �����
     */
    public Tape(String chunkFileDir) throws IOException {
        this.chunkFileDir = chunkFileDir;
        this.chunkFile = new File(chunkFileDir);
        chunkFile.createNewFile();
    }

    /**
     * ����� ���������� ���� ���������� ������� ��������������� �����
     * @return ���������� ���� � �������
     */
    public File getChunkFile() {
        return chunkFile;
    }

    /**
     * �����, ������� �������� ������ ���� � �������
     * � ������� �� ���� ��� ���������� chunkFileDir
     * ������ ������ ����.
     * @throws IOException ������, ������� ����� ��������� ��� �������� �����
     */
    public void refreshFile() throws IOException {
        chunkFile.delete();
        chunkFile = new File(chunkFileDir);
        chunkFile.createNewFile();
    }

    /**
     * ������� ����, � ������� ������ ��������
     */
    public void deleteFile() {
        this.chunkFile.delete();
    }
}
