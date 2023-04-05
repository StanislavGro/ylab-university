package io.ylab.intensive.lesson05.messagefilter.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.StringTokenizer;

// Этот класс я сделал лишь для удобства перевода слов из линии в столбец. Можете задействовать для тестов

// Позволяет сделать из большой строки плохих слов слова,
// которые будут идти в столбец
@Slf4j
public class FileConverter {
    private static final String INPUT_FILE_PATH = ".\\src\\main\\resources\\files\\input_file.txt";
    private static final String RESULT_FILE_PATH = ".\\src\\main\\resources\\files\\result_file.txt";

    public static File createObsceneWordsFile(File inputFile) throws IOException {
        if(inputFile == null || !inputFile.exists()) {
            throw new IOException("Входящий файл пуст или не существует!");
        }
        File output_file = new File(RESULT_FILE_PATH);
        if (!output_file.exists()) {
            output_file.createNewFile();
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(output_file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, ", ");
                while (stringTokenizer.hasMoreTokens()) {
                    String token = stringTokenizer.nextToken();
                    bufferedWriter.write(token + "\n");
                }
            }
        }
        return output_file;
    }

//    public static void main(String[] args) {
//        File inputFile = new File(INPUT_FILE_PATH);
//        try {
//            File resultObsceneFile = createObsceneWordsFile(inputFile);
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }

    public static File getResultFile() throws IOException {
        File inputFile = new File(INPUT_FILE_PATH);
        File resultFile = createObsceneWordsFile(inputFile);
        if(resultFile.exists())
            return resultFile;
        return null;
    }
}
