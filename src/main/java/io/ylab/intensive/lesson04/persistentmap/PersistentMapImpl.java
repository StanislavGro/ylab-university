package io.ylab.intensive.lesson04.persistentmap;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {
    private DataSource dataSource;
    private String currentMap;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        this.currentMap = name;
        String initInsertQuery = "INSERT INTO persistent_map (map_name, KEY, value) VALUES (?, NULL, NULL)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(initInsertQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        String containsKeyQuery = "SELECT COUNT(*) AS count FROM persistent_map " +
                "WHERE map_name = ? " +
                "and key = ? " +
                "and value IS NOT NULL";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(containsKeyQuery)) {
            preparedStatement.setString(1, currentMap);
            preparedStatement.setString(2, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                resultSet.close();
                return count > 0;
            }
            resultSet.close();
            return false;
        }
    }

    @Override
    public List<String> getKeys() throws SQLException {
        String selectQuery = "SELECT key FROM persistent_map WHERE map_name = ? and value IS NOT NULL";
        List<String> keyList = new LinkedList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, currentMap);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                keyList.add(resultSet.getString("key"));
            }
        }
        return keyList;
    }

    @Override
    public String get(String key) throws SQLException {
        String selectQuery = "SELECT value FROM persistent_map WHERE map_name = ? and key = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, currentMap);
            preparedStatement.setString(2, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                resultSet.close();
                return resultSet.getString("value");
            }
            resultSet.close();
            return null;
        }
    }

    @Override
    public void remove(String key) throws SQLException {
        String selectQuery = "DELETE FROM persistent_map WHERE map_name = ? and key = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, currentMap);
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        remove(key);
        String selectQuery = "INSERT INTO persistent_map (map_name, key, value) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, currentMap);
            preparedStatement.setString(2, key);
            if (value == null) {
                preparedStatement.setNull(3, Types.VARCHAR);
            } else {
                preparedStatement.setString(3, value);
            }
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void clear() throws SQLException {
        String selectQuery = "DELETE FROM persistent_map WHERE map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, currentMap);
            preparedStatement.executeUpdate();
        }
    }
}
