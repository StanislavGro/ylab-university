package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.dao.PersonDAO;
import io.ylab.intensive.lesson05.eventsourcing.request.DeleteRequest;
import io.ylab.intensive.lesson05.eventsourcing.request.PostRequest;
import io.ylab.intensive.lesson05.eventsourcing.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class DbApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        RabbitMQService rabbitMQService = applicationContext.getBean(RabbitMQService.class);
        PersonDAO personDAO = applicationContext.getBean(PersonDAO.class);
        // тут пишем создание и запуск приложения работы с БД
        try {
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse response = rabbitMQService.receiveMessage();
                if (response != null) {
                    String message = new String(response.getBody());
                    ObjectMapper objectMapper = new ObjectMapper();
                    if (message.contains("\"method\":\"POST\"")) {
                        // Получаем как раз таки запрос содержащий в себе Person на добавление
                        PostRequest postRequest = objectMapper.readValue(message, PostRequest.class);
                        Person person = postRequest.getPerson();
                        personDAO.savePerson(person.getId(), person.getName(), person.getLastName(), person.getMiddleName());
                    } else if (message.contains("\"method\":\"DELETE\"")) {
                        // Получаем как раз таки запрос содержащий в себе id на удаление
                        DeleteRequest deleteRequest = objectMapper.readValue(message, DeleteRequest.class);
                        Long id = deleteRequest.getId();
                        personDAO.deletePerson(id);
                    } else {
                        log.error("Такого типа запроса нет");
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            applicationContext.stop();
        }
    }
}
