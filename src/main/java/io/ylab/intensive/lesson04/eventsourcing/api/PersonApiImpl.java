package io.ylab.intensive.lesson04.eventsourcing.api;

import io.ylab.intensive.lesson04.eventsourcing.Person;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Тут пишем реализацию
 */
@Slf4j
public class PersonApiImpl implements PersonApi {
    private DataSource dataSource;

    public PersonApiImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) throws SQLException {
        Person person = findPerson(personId);
        if(person == null) {
            log.warn("Попытка удаления Person с id = " + personId +". Однако такого person не существует");
        } else {
            String deleteQuery = "DELETE FROM person WHERE person_id = ?;";
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setLong(1, personId);
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) throws SQLException {
        Person person = findPerson(personId);
        if(person != null) {
            String updateQuery = "UPDATE person SET first_name = ?, last_name = ?, middle_name = ? where person_id = ?;";
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                if (firstName == null || firstName.equals("")) {
                    preparedStatement.setNull(1, Types.VARCHAR);
                } else {
                    preparedStatement.setString(1, firstName);
                }
                if (lastName == null || lastName.equals("")) {
                    preparedStatement.setNull(2, Types.VARCHAR);
                } else {
                    preparedStatement.setString(2, lastName);
                }
                if (middleName == null || middleName.equals("")) {
                    preparedStatement.setNull(3, Types.VARCHAR);
                } else {
                    preparedStatement.setString(3, middleName);
                }
                preparedStatement.setLong(4, personId);
                preparedStatement.executeUpdate();
                log.info("Person с id = " + personId + " был успешно обновлен!");
            }
        } else {
            String insertQuery = "INSERT INTO person VALUES (?, ?, ?, ?);";
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                if (personId == null) {
                    preparedStatement.setNull(1, Types.BIGINT);
                } else {
                    preparedStatement.setLong(1, personId);
                }
                if (firstName == null || firstName.equals("")) {
                    preparedStatement.setNull(2, Types.VARCHAR);
                } else {
                    preparedStatement.setString(2, firstName);
                }
                if (lastName == null || lastName.equals("")) {
                    preparedStatement.setNull(3, Types.VARCHAR);
                } else {
                    preparedStatement.setString(3, lastName);
                }
                if (middleName == null || middleName.equals("")) {
                    preparedStatement.setNull(4, Types.VARCHAR);
                } else {
                    preparedStatement.setString(4, middleName);
                }
                preparedStatement.executeUpdate();
                log.info("Person с id = " + personId + " был успешно добавлен!");
            }
        }
    }

    @Override
    public Person findPerson(Long personId) throws SQLException {
        Person person = null;
        String selectQuery = "SELECT * FROM person WHERE person_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                person = new Person(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
            }
            resultSet.close();
            if(person == null) {
                log.info("Person с id = " + personId + " не существует");
            } else {
                log.info("Получение объекта Person с id = " + personId);
            }
            return person;
        }
    }

    @Override
    public List<Person> findAll() throws SQLException {
        List<Person> people = new LinkedList<>();
        String selectQuery = "SELECT * FROM person;";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                Person temp = new Person(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                people.add(temp);
            }
        }
        log.info("Получение списка Person с БД");
        return people;
    }
}
