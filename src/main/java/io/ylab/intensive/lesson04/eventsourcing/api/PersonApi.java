package io.ylab.intensive.lesson04.eventsourcing.api;

import java.sql.SQLException;
import java.util.List;

import io.ylab.intensive.lesson04.eventsourcing.Person;

public interface PersonApi {
  void deletePerson(Long personId) throws SQLException;

  void savePerson(Long personId, String firstName, String lastName, String middleName) throws SQLException;

  Person findPerson(Long personId) throws SQLException;

  List<Person> findAll() throws SQLException;
}
