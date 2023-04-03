package io.ylab.intensive.lesson04.filesort;

import io.ylab.intensive.lesson04.DbUtil;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
public class FileSorterTest {
    public static void main(String[] args) {
        try {
            DataSource dataSource = initDb();
            int count = 1_000_000;
            // Генерируем файл (из прошлой дз)
            File data = new Generator().generate(".\\src\\main\\resources\\files\\data.txt", count);
            FileSorter fileSorter = new FileSortImpl(dataSource);
            File res = fileSorter.sort(data);
        } catch (SQLException | IOException e) {
            log.error(e.getMessage());
        }
    }

    public static DataSource initDb() throws SQLException {
        String createSortTable = ""
                + "drop table if exists numbers;"
                + "CREATE TABLE if not exists numbers (\n"
                + "\tval bigint\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createSortTable, dataSource);
        return dataSource;
    }
}
