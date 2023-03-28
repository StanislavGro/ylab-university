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
            persistentMap.init("first map");
            persistentMap.put("11", "11");
            persistentMap.put("vdvd", null);
            persistentMap.put("22", "bfbf");
            System.out.println(persistentMap.getKeys());
            // false, потому что значения у данного ключа нет
            System.out.println(persistentMap.containsKey("vdvd"));
            System.out.println(persistentMap.containsKey("22"));
            persistentMap.remove("11");
            System.out.println(persistentMap.getKeys());
            persistentMap.init("second map");
            persistentMap.put("83", "55");
            persistentMap.clear();
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
