package io.ylab.intensive.lesson05.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

// Данный класс представляет конфигурацию бинов, в котором
// создаются объекты (такие, как DataSource и ConnectionFactory), управляемые контейнером spring.
// Каждый бин будет создан по одному экземпляру (scope singleton)
@Configuration
@ComponentScan(basePackages = "io.ylab.intensive.lesson05.eventsourcing")
public class Config {
    private DataSource dataSource;

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDatabaseName("postgres");
        dataSource.setPortNumber(5432);
        this.dataSource = dataSource;
        return dataSource;
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

    // Объявления бина Connection не было сделано, так как это плохо и лучше использовать пул соединений HikariCP,
    // но было решено не реализовывать это

    // Позволит закрыть соединение с бд перед уничтожением бина
    @PreDestroy
    public void destroy() throws Exception {
        if (this.dataSource != null) {
            this.dataSource.getConnection().close();
        }
    }
}
