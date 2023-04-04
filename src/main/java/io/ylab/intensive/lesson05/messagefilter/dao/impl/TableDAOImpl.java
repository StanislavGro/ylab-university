package io.ylab.intensive.lesson05.messagefilter.dao.impl;

import io.ylab.intensive.lesson05.DbUtil;
import io.ylab.intensive.lesson05.messagefilter.dao.TableDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

@Component
public class TableDAOImpl implements TableDAO {
    private final DataSource dataSource;
    private final static String TABLE_NAME = "message_filter";

    public TableDAOImpl(@Autowired DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean isTableExist() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, TABLE_NAME, null);
            boolean isExist = resultSet.next();
            resultSet.close();
            return isExist;
        }
    }

    @Override
    public void cleanTable() throws SQLException {
        if (!isTableExist()) {
            String createTableQuery = ""
                    + "CREATE TABLE message_filter (\n"
                    + "\tid bigserial NOT NULL,\n"
                    + "\tobscene_word varchar,\n"
                    + "\tCONSTRAINT mesfil_pkey PRIMARY KEY (id)\n"
                    + ");";
            DbUtil.applyDdl(createTableQuery, dataSource);
        } else {
            String deleteQuery = "DELETE FROM message_filter;";
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate(deleteQuery);
            }
        }
    }

    @Override
    public void fillTableFromFile(File file) throws SQLException, IOException {
        if (file == null || !file.exists()) {
            throw new IOException("Ошибка! Такого файла не существует!");
        }
        cleanTable();
        String insertQuery = "INSERT INTO message_filter (obscene_word) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
             BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String word;
            while ((word = bufferedReader.readLine()) != null) {
                preparedStatement.setString(1, word);
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public boolean isWordExist(String message) throws SQLException {
        String selectQuery = "SELECT * FROM message_filter WHERE obscene_word = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, message);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean result = resultSet.next();
            resultSet.close();
            return result;
        }
    }
}
