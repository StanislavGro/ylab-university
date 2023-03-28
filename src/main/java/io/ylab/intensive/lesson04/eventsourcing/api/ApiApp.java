package io.ylab.intensive.lesson04.eventsourcing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.request.Request;
import io.ylab.intensive.lesson04.eventsourcing.request.RequestMethod;
import io.ylab.intensive.lesson04.eventsourcing.request.body.PostBody;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ApiApp {
    private final static String QUEUE_NAME = "person_queue";
    private final static String EXCHANGE_NAME = "person_exchange";
    private final static String ROUTING_KEY = "person";
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = initMQ();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            Person person = new Person(1L, "Stanislav", "Grokhotov", "Olegovich");
            Request postRequest = new Request(RequestMethod.POST, new PostBody(person));
            sendRequest(channel, postRequest);
        }
    }

    private static void sendRequest(Channel channel, Request request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(request);
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
        log.info("Sent save request: \n" + message);
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}
