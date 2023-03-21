package ru.yalabuniversity.homework.lecture3.datedmap;

public class DatedMapTest {
    public static void main(String[] args) throws InterruptedException {
        DatedMap datedMap = new DatedMapImpl();
        datedMap.put("first", "Hello");
        datedMap.put("second", "ylab");
        // Такой ключ есть, вернем результат, вернет Hello
        System.out.println(datedMap.get("first"));
        // Такого ключа у нас нет, вернет null
        System.out.println(datedMap.get("third"));
        // Ключ имеется, вернет true
        System.out.println(datedMap.containsKey("second"));
        // А вот такого ключа нет, вернется false
        System.out.println(datedMap.containsKey("fourth"));
        datedMap.put("third", "!");
        // [third, first, second]
        System.out.println(datedMap.keySet());
        // Вернем последнее время добавление ключа second
        System.out.println(datedMap.getKeyLastInsertionDate("second"));
        // Заснем на секунду
        Thread.sleep(1000);
        datedMap.put("second", "YLAB");
        // И еще раз
        System.out.println(datedMap.getKeyLastInsertionDate("second"));
        // А теперь удалим ключ third и попытаемся найти его время, вернет null
        datedMap.remove("third");
        System.out.println(datedMap.getKeyLastInsertionDate("third"));
        // Ну и проверим есть ли оно
        System.out.println(datedMap.containsKey("third"));
    }
}
