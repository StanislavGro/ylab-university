package io.ylab.intensive.lesson05.eventsourcing.dao;

import io.ylab.intensive.lesson05.eventsourcing.Person;

import java.sql.SQLException;
import java.util.List;

public interface PostgresDAO {
    void deletePerson(Long personId) throws SQLException;

    void savePerson(Long personId, String firstName, String lastName, String middleName) throws SQLException;

    Person findPerson(Long personId) throws SQLException;

    List<Person> findAll() throws SQLException;
}
