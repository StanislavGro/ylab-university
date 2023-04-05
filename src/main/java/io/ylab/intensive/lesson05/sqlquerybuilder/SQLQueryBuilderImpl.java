package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {
    private final DataSource dataSource;

    public SQLQueryBuilderImpl(@Autowired DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        if (!isTableExist(tableName)) {
            return null;
        }
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getColumns(null, null, tableName, null);
            StringBuilder sqlQuery = new StringBuilder("SELECT ");
            if (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                sqlQuery.append(columnName);
            }
            while (resultSet.next()) {
                sqlQuery.append(", ");
                String columnName = resultSet.getString("COLUMN_NAME");
                sqlQuery.append(columnName);
            }
            sqlQuery.append(" FROM ");
            sqlQuery.append(tableName);
            sqlQuery.append(";");
            resultSet.close();
            return sqlQuery.toString();
        }
    }

    @Override
    public List<String> getTables() throws SQLException {
        List<String> tableList = new LinkedList<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                tableList.add(tableName);
            }
            resultSet.close();
            return tableList;
        }
    }

    @Override
    public boolean isTableExist(String tableName) throws SQLException {
        if (tableName == null) {
            throw new SQLException("Имя таблицы не должно быть null");
        }
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, null);
            boolean result = resultSet.next();
            resultSet.close();
            return result;
        }
    }
}