package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.dao.PersonDAO;
import io.ylab.intensive.lesson05.eventsourcing.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

// Представляет собой сервис для работы с Person
@Slf4j
@Component
public class PersonApiImpl implements PersonApi {
    private final PersonDAO personDAO;
    private final RabbitMQService rabbitMQService;

    public PersonApiImpl(@Autowired PersonDAO personDAO, @Autowired RabbitMQService rabbitMQService) {
        this.personDAO = personDAO;
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
        return personDAO.findPerson(personId);
    }

    @Override
    public List<Person> findAll() throws SQLException {
        return personDAO.findAll();
    }
}
