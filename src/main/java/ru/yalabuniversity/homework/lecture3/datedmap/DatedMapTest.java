package ru.yalabuniversity.homework.lecture3.datedmap;

public class DatedMapTest {
    public static void main(String[] args) throws InterruptedException {
        DatedMap datedMap = new DatedMapImpl();
        datedMap.put("first", "Hello");
        datedMap.put("second", "ylab");
        // ����� ���� ����, ������ ���������, ������ Hello
        System.out.println(datedMap.get("first"));
        // ������ ����� � ��� ���, ������ null
        System.out.println(datedMap.get("third"));
        // ���� �������, ������ true
        System.out.println(datedMap.containsKey("second"));
        // � ��� ������ ����� ���, �������� false
        System.out.println(datedMap.containsKey("fourth"));
        datedMap.put("third", "!");
        // [third, first, second]
        System.out.println(datedMap.keySet());
        // ������ ��������� ����� ���������� ����� second
        System.out.println(datedMap.getKeyLastInsertionDate("second"));
        // ������ �� �������
        Thread.sleep(1000);
        datedMap.put("second", "YLAB");
        // � ��� ���
        System.out.println(datedMap.getKeyLastInsertionDate("second"));
        // � ������ ������ ���� third � ���������� ����� ��� �����, ������ null
        datedMap.remove("third");
        System.out.println(datedMap.getKeyLastInsertionDate("third"));
        // �� � �������� ���� �� ���
        System.out.println(datedMap.containsKey("third"));
    }
}
