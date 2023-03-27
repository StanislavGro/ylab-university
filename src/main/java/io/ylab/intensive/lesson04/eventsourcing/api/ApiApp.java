package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;

import javax.sql.DataSource;

public class ApiApp {
  public static void main(String[] args) throws Exception {
    String exchangeName = "person_exchange";
    String queueName = "person_queue";
    ConnectionFactory connectionFactory = initMQ();
    try (Connection connection = connectionFactory.newConnection();
         Channel channel = connection.createChannel()) {
      channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
      channel.queueDeclare(queueName, true, false, false, null);
      channel.queueBind(queueName, exchangeName, "*");
      channel.basicPublish(exchangeName, "key", null, "Hello".getBytes());
    }
//    DataSource dataSource = DbUtil.buildDataSource();
//    PersonApi personApi = new PersonApiImpl(dataSource);
//    personApi.savePerson(1L, "Stanislav", "Grokhotov", "Olegovich");
//    personApi.savePerson(2L, "Eva", "Savelieva", "Robertovna");
//    personApi.savePerson(3L, "Sergey", "Suprunchuk", null);
//    System.out.println(personApi.findAll());
//    personApi.savePerson(3L, "Sergey", "Suprunchuk", "Vitalievich");
//    System.out.println(personApi.findAll());
//    System.out.println(personApi.findPerson(2L));
//    System.out.println(personApi.findPerson(15L));
//    personApi.deletePerson(3L);

  }

  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }
}
