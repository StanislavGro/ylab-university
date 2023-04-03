package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.dao.PostgresDAO;
import io.ylab.intensive.lesson05.eventsourcing.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component
public class PersonApiImpl implements PersonApi {
    private final PostgresDAO postgresDAO;
    private final RabbitMQService rabbitMQService;

    public PersonApiImpl(@Autowired PostgresDAO postgresDAO, @Autowired RabbitMQService rabbitMQService) {
        this.postgresDAO = postgresDAO;
        this.rabbitMQService = rabbitMQService;
    }

    @Override
    public void deletePerson(Long personId) {
        rabbitMQService.sendDeleteMessage(personId);
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        Person person = new Person(personId, firstName, lastName, middleName);
        rabbitMQService.sendSaveMessage(person);
    }

    @Override
    public Person findPerson(Long personId) throws SQLException {
        return postgresDAO.findPerson(personId);
    }

    @Override
    public List<Person> findAll() throws SQLException {
        return postgresDAO.findAll();
    }
}
