package io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;

public class PersistenceMapTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);
    persistentMap.init("first map");
    persistentMap.put("11", "11");
    persistentMap.put("vdvd", null);
    persistentMap.put("22", "bfbf");
    System.out.println(persistentMap.getKeys());
    System.out.println(persistentMap.containsKey("vdvd"));
    System.out.println(persistentMap.containsKey("22"));
    persistentMap.remove("11");
    System.out.println(persistentMap.getKeys());
    persistentMap.clear();
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