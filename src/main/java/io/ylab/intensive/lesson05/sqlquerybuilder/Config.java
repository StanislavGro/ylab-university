package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@Configuration
@ComponentScan("io.ylab.intensive.lesson05.sqlquerybuilder")
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

    @PreDestroy
    public void destroy() throws Exception {
        if (this.dataSource != null) {
            this.dataSource.getConnection().close();
        }
    }
}
