package ru.yalabuniversity.homework.lecture3.orgstructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrgStructureParserImpl implements OrgStructureParser {
    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        // ��������� ������ �� ���. ���������, ����� � ���������� ����������
        Employee boss = null;
        // ������ ���� �������� id ��������� � ���� ��������
        Map<Long, Employee> employeeMap = new HashMap<>();
        // ������ ���� �������� � ���� ������ ������ ����������� ����������� � id ���������
        Map<Long, List<Employee>> subordinatesMap = new HashMap<>();
        // ���������� try-catch with resources ��� �������� �������� ������, ���� ���������� ������������� ���� �� ����� � �������������� ���-��
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {
            // ������ ������ � ������ �������� ��� �� �����, ������ ������� � �� ���������
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parsedLine = line.split(";");
                Long employeeId = Long.parseLong(parsedLine[0]);
                // ���� ��� ���� ���� ��������� � �������� ���� ����
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
                    List<Employee> subordinates = subordinatesMap.getOrDefault(bossId, new ArrayList<>()); // �� ������� ArrayList, ���� � �� ������������ LinkedList
                    subordinates.add(employee);
                    subordinatesMap.put(bossId, subordinates);
                }
                employeeMap.put(employeeId, employee);
            }
        }
        addSubordinates(employeeMap, subordinatesMap);
        return boss;
    }

    private void addSubordinates(Map<Long, Employee> employeeMap, Map<Long, List<Employee>> subordinatesMap) {
        // ������ ��������� �� ������ ���������� � ������� ������� ������ ����������� � ������. ��������� O(n * (log2(n)) ^ 2)
        for (Employee employee : employeeMap.values()) {
            employee.setBoss(employeeMap.getOrDefault(employee.getBossId(), null));
            employee.setSubordinates(subordinatesMap.getOrDefault(employee.getId(), null));
        }
    }
}
