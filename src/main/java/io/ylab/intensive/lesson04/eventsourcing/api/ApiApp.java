package io.ylab.intensive.lesson04.eventsourcing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.request.DeleteRequest;
import io.ylab.intensive.lesson04.eventsourcing.request.PostRequest;
import io.ylab.intensive.lesson04.eventsourcing.request.Request;
import io.ylab.intensive.lesson04.eventsourcing.request.RequestMethod;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@Slf4j
public class ApiApp {
    private final static String QUEUE_NAME = "person_queue";
    private final static String EXCHANGE_NAME = "person_exchange";
    private final static String ROUTING_KEY = "person";

    public static void main(String[] args) throws Exception {
        DataSource dataSource = DbUtil.buildDataSource();
        PersonApi personApi = new PersonApiImpl(dataSource);
        ConnectionFactory connectionFactory = initMQ();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            Person person1 = new Person(1L, "Stanislav", "Grokhotov", "Olegovich");
            Person person2 = new Person(2L, "Eva", "Savelieva", "Robertovna");
            Person person3 = new Person(3L, "Sergey", "Suprunchuk", null);
            sendRequest(channel, new PostRequest(person1));
            sendRequest(channel, new PostRequest(person2));
            sendRequest(channel, new PostRequest(person3));
            List<Person> people = personApi.findAll();
            System.out.println(people);
            Person person4 = new Person(3L, "Sergey", "Suprunchuk", "Vitalievich");
            sendRequest(channel, new PostRequest(person4));
            List<Person> people2 = personApi.findAll();
            System.out.println(people2);
            System.out.println(personApi.findAll());
            System.out.println(personApi.findPerson(2L));
            System.out.println(personApi.findPerson(15L));
            sendRequest(channel, new DeleteRequest(3L));
            List<Person> people3 = personApi.findAll();
            System.out.println(people3);
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
