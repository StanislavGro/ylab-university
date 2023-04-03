package io.ylab.intensive.lesson05.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

// Данный класс представляет конфигурацию бинов, в котором
// создаются объекты (DataSource и ConnectionFactory),
// управляемые контейнером spring.
// Каждый бин будет создан по одному экземпляру (scope singleton)
@Configuration
@ComponentScan(basePackages = "io.ylab.intensive.lesson05.eventsourcing")
public class Config {
    private final static String QUEUE_NAME = "person_queue";
    private final static String EXCHANGE_NAME = "person_exchange";
    private final static String ROUTING_KEY = "person";
    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDatabaseName("postgres");
        dataSource.setPortNumber(5432);
        return dataSource;
    }

    @Bean
    public Connection psqlConnection() throws SQLException {
        return dataSource().getConnection();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public com.rabbitmq.client.Connection rabMQConnection() throws IOException, TimeoutException {
        return connectionFactory().newConnection();
    }

    @Bean
    public Channel channel() throws IOException, TimeoutException {
        Channel channel = rabMQConnection().createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        return channel;
    }
}
