package io.ylab.intensive.lesson04.eventsourcing.api;

import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Тут пишем реализацию
 */


//TODO проверь что все закрыл!!
public class PersonApiImpl implements PersonApi {
    private DataSource dataSource;

    public PersonApiImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) {

    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {

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
        return people;
    }
}
