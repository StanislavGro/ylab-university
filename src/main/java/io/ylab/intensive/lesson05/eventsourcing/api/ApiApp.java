package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@Slf4j
public class ApiApp {
    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApiImpl.class);
        // Сохраняем их в бд
        personApi.savePerson(1L, "Stanislav", "Grokhotov", "Olegovich");
        personApi.savePerson(2L, "Eva", "Savelieva", "Robertovna");
        personApi.savePerson(3L, "Sergey", "Suprunchuk", null);
        List<Person> people = personApi.findAll();
        System.out.println(people);
        // Не нравится то, что middle name у предыдущего отсутствовал, поэтому изменим это
        personApi.savePerson(3L, "Sergey", "Suprunchuk", "Vitalievich");
        List<Person> people2 = personApi.findAll();
        System.out.println(people2);
        System.out.println(personApi.findAll());
        System.out.println(personApi.findPerson(2L));
        // Такого нет
        System.out.println(personApi.findPerson(15L));
        // Запрос на удаление последнего person
        personApi.deletePerson(3L);
        List<Person> people3 = personApi.findAll();
        System.out.println(people3);
        personApi.deletePerson(15L);
        // При первом запуске ApiApp все списки будут пустыми, а все запросы на получения будут давать null
        // Однако мы можем поставить на паузу поток и подождать данные в бд пока нужные запросы обрабатываются в DbApp
        // Также подметим что во второй раз не нужно перезапускать DbApp, пусть он и дальше слушает очередь. Перезапускать нужно
        // ApiApp который создаст новые запросы и отправит в rabbit, откуда их считает DbApp и если таблицы не пустая (с пред. шага),
        // начнет с самого начала выводить списки и конкретные элементы из нее
        Thread.sleep(3000);
        List<Person> people4 = personApi.findAll();
        System.out.println(people4);
    }
}
