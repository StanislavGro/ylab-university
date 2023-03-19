package ru.yalabuniversity.homework.lecture3.orgstructure.builder;

import ru.yalabuniversity.homework.lecture3.orgstructure.Employee;

import java.util.List;

public interface EmployeeBuilder {
    EmployeeBuilder withId(Long id);

    EmployeeBuilder withBossId(Long bossId);

    EmployeeBuilder withName(String name);

    EmployeeBuilder withPosition(String position);

    EmployeeBuilder withBoss(Employee boss);

    EmployeeBuilder withSubordinates(List<Employee> subordinates);

    Employee build();
}
