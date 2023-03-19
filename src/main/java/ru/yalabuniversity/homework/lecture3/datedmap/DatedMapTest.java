package ru.yalabuniversity.homework.lecture3.datedmap;

public class DatedMapTest {
    public static void main(String[] args) {
        DatedMap datedMap = new DatedMapImpl();
        datedMap.put("first", "Hello");
        datedMap.put("second", "ylab");
        System.out.println(datedMap.get("first"));
        //такого нет
        System.out.println(datedMap.get("third"));
        System.out.println(datedMap.containsKey("second"));
        //такого тоже нет
        System.out.println(datedMap.containsKey("fourth"));
        datedMap.put("third", "!");
        System.out.println(datedMap.keySet());
        System.out.println(datedMap.getKeyLastInsertionDate("second"));
        datedMap.put("second", "YLAB");
        System.out.println(datedMap.getKeyLastInsertionDate("second"));
    }
}
