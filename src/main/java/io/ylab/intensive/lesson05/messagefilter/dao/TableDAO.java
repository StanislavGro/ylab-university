package io.ylab.intensive.lesson05.messagefilter.dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public interface TableDAO {
    boolean isTableExist() throws SQLException;
    void cleanTable() throws SQLException;
    void fillTableFromFile(File file) throws SQLException, IOException;
    boolean isWordExist(String message) throws SQLException;
}
