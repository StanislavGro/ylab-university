package ru.yalabuniversity.homework.lecture3.orgstructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrgStructureParserImpl implements OrgStructureParser {
    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        Employee boss = null;
        Map<Long, Employee> employeeMap = new HashMap<>();
        Map<Long, List<Employee>> subordinatesMap = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {
            // ѕерва€ строка с данными таблицы, просто пропускаем ее
            bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parsedLine = line.split(";");

                Long employeeId = Long.parseLong(parsedLine[0]);
                Long bossId = parsedLine[1].equals("") ? null : Long.parseLong(parsedLine[1]);
                String employeeName = parsedLine[2];
                String employeePosition = parsedLine[3];

                Employee employee = Employee.builder()
                        .withId(employeeId)
                        .withBossId(bossId)
                        .withName(employeeName)
                        .withPosition(employeePosition)
                        .build();

                if (bossId == null) {
                    boss = employee;
                } else {
                    List<Employee> subordinates = subordinatesMap.getOrDefault(bossId, new LinkedList<>());
                    subordinates.add(employee);
                    subordinatesMap.put(bossId, subordinates);
                }

                if (employeeMap.containsKey(employeeId)) {
                    throw new IOException("–аботник с таким id уже записан!");
                }
                employeeMap.put(employeeId, employee);
            }
            addSubordinates(employeeMap, subordinatesMap);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
        return boss;
    }

    private void addSubordinates(Map<Long, Employee> employeeMap, Map<Long, List<Employee>> subordinatesMap) {
        for (Employee employee : employeeMap.values()) {
            employee.setBoss(employeeMap.getOrDefault(employee.getBossId(), null));
            employee.setSubordinates(subordinatesMap.getOrDefault(employee.getId(),null));
        }
    }
}
