package io.ylab.intensive.lesson05.sqlquerybuilder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class SQLQueryExtenderTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        try {
            SQLQueryBuilder queryBuilder = applicationContext.getBean(SQLQueryBuilder.class);
            List<String> tables = queryBuilder.getTables();
            // вот так сгенерируем запросы для всех таблиц что есть в БД
            for (String tableName : tables) {
                System.out.println(queryBuilder.queryForTable(tableName));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            applicationContext.close();
        }
    }
}
