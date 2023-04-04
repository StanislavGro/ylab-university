package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.ConnectionFactory;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@Configuration
@ComponentScan("io.ylab.intensive.lesson05.messagefilter")
public class Config {
    private DataSource dataSource;

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

    @PreDestroy
    public void destroy() throws Exception {
        if (this.dataSource != null) {
            this.dataSource.getConnection().close();
        }
    }
}
