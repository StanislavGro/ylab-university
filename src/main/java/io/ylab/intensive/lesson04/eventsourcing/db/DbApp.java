package io.ylab.intensive.lesson04.eventsourcing.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.api.PersonApi;
import io.ylab.intensive.lesson04.eventsourcing.api.PersonApiImpl;
import io.ylab.intensive.lesson04.eventsourcing.request.DeleteRequest;
import io.ylab.intensive.lesson04.eventsourcing.request.PostRequest;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class DbApp {
    public static void main(String[] args) {
        String queueName = "person_queue";
        DataSource dataSource = null;
        ConnectionFactory connectionFactory = null;
        try {
            dataSource = initDb();
            connectionFactory = initMQ();
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }
        PersonApi personApi = new PersonApiImpl(dataSource);
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(queueName, true);
                if (message != null) {
                    // Если в очереди что-то появилось, считывает это
                    String received = new String(message.getBody());
                    ObjectMapper objectMapper = new ObjectMapper();
                    // Проверяем, что если в считанном запросе есть Post (или Delete)
                    if (received.contains("\"method\":\"POST\"")) {
                        // Получаем как раз таки запрос содержащий в себе Person на добавление
                        PostRequest postRequest = objectMapper.readValue(received, PostRequest.class);
                        Person person = postRequest.getPerson();
                        personApi.savePerson(person.getId(), person.getName(), person.getLastName(), person.getMiddleName());
                    } else if (received.contains("\"method\":\"DELETE\"")) {
                        // Получаем как раз таки запрос содержащий в себе id на удаление
                        DeleteRequest deleteRequest = objectMapper.readValue(received, DeleteRequest.class);
                        Long id = deleteRequest.getId();
                        personApi.deletePerson(id);
                    } else {
                        log.error("Такого типа запроса нет");
                    }
                }
            }
        } catch (SQLException | IOException | TimeoutException e) {
            log.error(e.getMessage());
        }
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}
