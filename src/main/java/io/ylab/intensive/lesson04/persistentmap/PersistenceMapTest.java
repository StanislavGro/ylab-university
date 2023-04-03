package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.SQLException;

// TODO подумать еще раз правильно ли я работаю и над обработкой ошибок

@Slf4j
public class PersistenceMapTest {
    public static void main(String[] args) {
        try {
            DataSource dataSource = initDb();
            PersistentMap persistentMap = new PersistentMapImpl(dataSource);
            // Добавляем в мапу значения
            persistentMap.init("first map");
            persistentMap.put("dog", "Duke");
            persistentMap.put("cat", "Mimi");
            persistentMap.put("frog", "Elis");
            System.out.println(persistentMap.getKeys());
            // Такой есть - true
            System.out.println(persistentMap.containsKey("cat"));
            // Такого уже нет
            System.out.println(persistentMap.containsKey("22"));
            // Удаляем и смотрим. Cat уже нет в списке
            persistentMap.remove("cat");
            System.out.println(persistentMap.getKeys());
            // Снова добавляем и смотрим, теперь есть в списке
            persistentMap.put("cat", "Lunka");
            System.out.println(persistentMap.getKeys());
            // Создаем новую мапу
            PersistentMap persistentMap2 = new PersistentMapImpl(dataSource);
            persistentMap2.init("second map");
            // В этой мапе есть такой же ключ-значение как в первой
            persistentMap2.put("dog", "Duke");
            System.out.println(persistentMap2.get("dog"));
            // Значение должно замениться
            persistentMap2.put("dog", "Mike");
            System.out.println(persistentMap2.get("dog"));
            // А в первой - нет. Как был Duke так и должен остаться
            System.out.println(persistentMap.get("dog"));
            System.out.println(persistentMap2.getKeys());
            persistentMap2.clear();
            // Ничего не должно быть
            System.out.println(persistentMap2.getKeys());
            // Должно быть null
            System.out.println(persistentMap2.get("dog"));
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = ""
                + "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar,\n"
                + "   value varchar\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
