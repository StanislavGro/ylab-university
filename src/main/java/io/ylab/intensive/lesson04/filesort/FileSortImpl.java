package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

public class FileSortImpl implements FileSorter {
    private DataSource dataSource;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) throws SQLException, IOException {
        // Сначала мы сохраняем данные из файла в БД при помощи batch-processing, то есть частями
        insertValuesIntoTable(data, dataSource);
        // Подчищаем старый файл
        data = refreshFile(data);
        // Сортируем в БД и заново сохраняем в файл
        sortValuesFromTable(data, dataSource);
        return data;
    }

    private void insertValuesIntoTable(File data, DataSource dataSource) throws SQLException, IOException {
        // Беру изначальный batch равный 10000. Можно больше, главное чтобы оперативки хватило
        int batchSize = 10000;
        int size = 0;
        String insertQuery = "INSERT INTO numbers VALUES (?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
             BufferedReader bufferedReader = new BufferedReader(new FileReader(data))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                long value = Long.parseLong(line);
                preparedStatement.setLong(1, value);
                preparedStatement.addBatch();
                if (++size % batchSize == 0) {
                    preparedStatement.executeBatch();
                }
            }
            preparedStatement.executeBatch();
        }
    }

    private File refreshFile(File data) throws IOException {
        String dataPath = data.getAbsolutePath();
        data.delete();
        File newData = new File(dataPath);
        newData.createNewFile();
        return newData;
    }

    private void sortValuesFromTable(File data, DataSource dataSource) throws SQLException, IOException {
        // Запрос на получение данных с таблицы в убывающем порядке
        String selectQuery = "SELECT val FROM numbers ORDER BY val DESC;";
        String dropQuery = "DROP TABLE numbers;";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery);
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(data))) {
            while (resultSet.next()) {
                Long value = resultSet.getLong("val");
                bufferedWriter.write(value + "\n");
            }
            statement.executeUpdate(dropQuery);
        }
    }
}
