package io.ylab.intensive.lesson05.eventsourcing.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
import javax.sql.DataSource;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.DbUtil;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "io.ylab.intensive.lesson05.eventsourcing")
public class Config {
  private final static String QUEUE_NAME = "person_queue";
  private final static String EXCHANGE_NAME = "person_exchange";
  private final static String ROUTING_KEY = "person";
  @Bean
  public DataSource dataSource() throws SQLException {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setServerName("localhost");
    dataSource.setUser("postgres");
    dataSource.setPassword("postgres");
    dataSource.setDatabaseName("postgres");
    dataSource.setPortNumber(5432);

    String ddl = ""
                     + "drop table if exists person;"
                     + "create table if not exists person (\n"
                     + "person_id bigint primary key,\n"
                     + "first_name varchar,\n"
                     + "last_name varchar,\n"
                     + "middle_name varchar\n"
                     + ")";
    DbUtil.applyDdl(ddl, dataSource);
    
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
