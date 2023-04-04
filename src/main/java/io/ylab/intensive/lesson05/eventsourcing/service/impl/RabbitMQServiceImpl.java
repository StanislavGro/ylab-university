package io.ylab.intensive.lesson05.eventsourcing.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.request.DeleteRequest;
import io.ylab.intensive.lesson05.eventsourcing.request.PostRequest;
import io.ylab.intensive.lesson05.eventsourcing.request.Request;
import io.ylab.intensive.lesson05.eventsourcing.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// Сервис для работы с RabbitMQ
// Я бы добавил в эту папку PersonApi и PersonApiImpl, но это делать запрещено
@Slf4j
@Component
public class RabbitMQServiceImpl implements RabbitMQService {
    private final ConnectionFactory connectionFactory;
    private final static String QUEUE_NAME = "person_queue";
    private final static String EXCHANGE_NAME = "person_exchange";
    private final static String ROUTING_KEY = "person";

    public RabbitMQServiceImpl(@Autowired ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void sendSaveMessage(Person person) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            sendRequest(channel, new PostRequest(person));
        } catch (IOException | TimeoutException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void sendDeleteMessage(Long id) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            sendRequest(channel, new DeleteRequest(id));
        } catch (IOException | TimeoutException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public GetResponse receiveMessage() {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            GetResponse response = channel.basicGet(QUEUE_NAME, true);
            return response;
        } catch (IOException | TimeoutException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private static void sendRequest(Channel channel, Request request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(request);
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
        log.info("Отправлен запрос на сохранение/удаление: \n" + message);
    }
}
