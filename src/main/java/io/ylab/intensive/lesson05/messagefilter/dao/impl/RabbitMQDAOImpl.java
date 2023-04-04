package io.ylab.intensive.lesson05.messagefilter.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import io.ylab.intensive.lesson05.eventsourcing.request.PostRequest;
import io.ylab.intensive.lesson05.messagefilter.dao.RabbitMQDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
public class RabbitMQDAOImpl implements RabbitMQDAO {
    private final ConnectionFactory connectionFactory;
    private final static String INPUT_QUEUE_NAME = "input";
    private final static String OUTPUT_QUEUE_NAME = "output";
    private final static String EXCHANGE_NAME = "message_exchange";
    private final static String ROUTING_KEY = "message";

    public RabbitMQDAOImpl(@Autowired ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public String receiveMessage() {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            GetResponse response = channel.basicGet(INPUT_QUEUE_NAME, true);
            if(response != null) {
                String message = new String(response.getBody());
                if(!message.equals("")) {
                    log.info("Было получено сообщение из очереди " + INPUT_QUEUE_NAME);
                }
                return message;
            }
            return null;
        } catch (IOException | TimeoutException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void sendMessage(String message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(OUTPUT_QUEUE_NAME, true, false, false, null);
            channel.queueBind(OUTPUT_QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
            log.info("Было отправлено обработанное сообщение в очередь " + OUTPUT_QUEUE_NAME);
        } catch (IOException | TimeoutException e) {
            log.error(e.getMessage());
        }
    }
}
