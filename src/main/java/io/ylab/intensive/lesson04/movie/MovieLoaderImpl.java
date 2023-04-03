package io.ylab.intensive.lesson04.movie;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class MovieLoaderImpl implements MovieLoader {
    private DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            // Пропускаем 2 первые строки содержащие поясняющую информацию
            bufferedReader.readLine();
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Разделяем записи в csv по разделителю;
                String[] parsedLine = line.split(";");
                // Если какое-то поле отсутствует (в данном случае, если оно отсутствует, то будет пустая строка ""), то мы сохраняем null
                // Иначе значение
                Integer year = parsedLine[0].equals("") ? null : Integer.parseInt(parsedLine[0]);
                Integer length = parsedLine[1].equals("") ? null : Integer.parseInt(parsedLine[1]);
                String title = parsedLine[2].equals("") ? null : parsedLine[2];
                String subject = parsedLine[3].equals("") ? null : parsedLine[3];
                String actors = parsedLine[4].equals("") ? null : parsedLine[4];
                String actress = parsedLine[5].equals("") ? null : parsedLine[5];
                String director = parsedLine[6].equals("") ? null : parsedLine[6];
                Integer popularity = parsedLine[7].equals("") ? null : Integer.parseInt(parsedLine[7]);
                Boolean awards = parsedLine[8].equals("") ? null : Boolean.parseBoolean(parsedLine[8]);
                Movie movie = Movie.builder()
                        .withYear(year)
                        .withLength(length)
                        .withTitle(title)
                        .withSubject(subject)
                        .withActors(actors)
                        .withActress(actress)
                        .withDirector(director)
                        .withPopularity(popularity)
                        .withAwards(awards)
                        .build();
                saveMovie(movie, dataSource);
            }
        }
    }

    private void saveMovie(Movie movie, DataSource dataSource) {
        String insertQuery = "INSERT INTO movie (year, length, title, subject, actors, actress, director, popularity, awards)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            if (movie.getYear() == null) {
                preparedStatement.setNull(1, Types.INTEGER);
            } else {
                preparedStatement.setInt(1, movie.getYear());
            }
            if (movie.getLength() == null) {
                preparedStatement.setNull(2, Types.INTEGER);
            } else {
                preparedStatement.setInt(2, movie.getLength());
            }
            if (movie.getTitle() == null) {
                preparedStatement.setNull(3, Types.VARCHAR);
            } else {
                preparedStatement.setString(3, movie.getTitle());
            }
            if (movie.getSubject() == null) {
                preparedStatement.setNull(4, Types.VARCHAR);
            } else {
                preparedStatement.setString(4, movie.getSubject());
            }
            if (movie.getActors() == null) {
                preparedStatement.setNull(5, Types.VARCHAR);
            } else {
                preparedStatement.setString(5, movie.getActors());
            }
            if (movie.getActress() == null) {
                preparedStatement.setNull(6, Types.VARCHAR);
            } else {
                preparedStatement.setString(6, movie.getActress());
            }
            if (movie.getDirector() == null) {
                preparedStatement.setNull(7, Types.VARCHAR);
            } else {
                preparedStatement.setString(7, movie.getDirector());
            }
            if (movie.getPopularity() == null) {
                preparedStatement.setNull(8, Types.INTEGER);
            } else {
                preparedStatement.setInt(8, movie.getPopularity());
            }
            if (movie.getAwards() == null) {
                preparedStatement.setNull(9, Types.BOOLEAN);
            } else {
                preparedStatement.setBoolean(9, movie.getAwards());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
