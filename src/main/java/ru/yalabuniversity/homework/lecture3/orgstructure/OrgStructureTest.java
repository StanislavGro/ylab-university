package ru.yalabuniversity.homework.lecture3.orgstructure;

import java.io.File;
import java.io.IOException;

public class OrgStructureTest {
    public static void main(String[] args) {
        OrgStructureParser orgStructureParser = new OrgStructureParserImpl();
        File file = new File(".\\src\\main\\resources\\files\\organization_structure.csv");
        try {
            Employee boss = orgStructureParser.parseStructure(file);
            if (boss != null) {
                System.out.println(boss.getId());
                System.out.println(boss.getBossId());
                System.out.println(boss.getBoss());
                System.out.println(boss.getName());
                System.out.println(boss.getPosition());
                for (Employee employee : boss.getSubordinates()) {
                    System.out.println(employee.getId() + " " + employee.getName() + " " + employee.getPosition());
                }
            } else {
                throw new IOException("Вы передали пустой файл или файл, в котором нет босса");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
