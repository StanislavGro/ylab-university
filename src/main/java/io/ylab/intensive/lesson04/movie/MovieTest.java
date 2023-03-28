package io.ylab.intensive.lesson04.movie;

import io.ylab.intensive.lesson04.DbUtil;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
public class MovieTest {
    public static void main(String[] args) {
        try {
            DataSource dataSource = initDb();
            MovieLoader movieLoader = new MovieLoaderImpl(dataSource);

            File dataFile = new File(".\\src\\main\\resources\\files\\film.csv");
            movieLoader.loadData(dataFile);
        } catch (SQLException | IOException e) {
            log.error(e.getMessage());
        }
//        String selectGroupBy = "SELECT subject, count(*) AS count FROM movie GROUP BY subject;";
//        try (Connection connection = dataSource.getConnection();
//             Statement statement = connection.createStatement();
//             ResultSet rs = statement.executeQuery(selectGroupBy)) {
//            String columnNameOne = rs.getMetaData().getColumnName(1);
//            String columnNameTwo = rs.getMetaData().getColumnName(2);
//            System.out.println(columnNameOne + "\t|\t" + columnNameTwo);
//            while (rs.next()) {
//                String columnValueOne = rs.getString(1);
//                String columnValueTwo = rs.getString(2);
//                System.out.println((columnValueOne == null ? "" : columnValueOne) + "\t|\t" + columnValueTwo);
//            }
//        }
//        P.S. реализация в основном для себя
    }

    private static DataSource initDb() throws SQLException {
        String createMovieTable = "drop table if exists movie;"
                + "CREATE TABLE IF NOT EXISTS movie (\n"
                + "\tid bigserial NOT NULL,\n"
                + "\t\"year\" int4,\n"
                + "\tlength int4,\n"
                + "\ttitle varchar,\n"
                + "\tsubject varchar,\n"
                + "\tactors varchar,\n"
                + "\tactress varchar,\n"
                + "\tdirector varchar,\n"
                + "\tpopularity int4,\n"
                + "\tawards bool,\n"
                + "\tCONSTRAINT movie_pkey PRIMARY KEY (id)\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMovieTable, dataSource);
        return dataSource;
    }
}
