package ru.yalabuniversity.homework.lecture3.orgstructure.builder;

import ru.yalabuniversity.homework.lecture3.orgstructure.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeBuilderImpl implements EmployeeBuilder{
    private Long id;
    private Long bossId;
    private String name;
    private String position;
    private Employee boss;
    private List<Employee> subordinates = new ArrayList<>();

    public EmployeeBuilderImpl() {
        super();
    }

    @Override
    public EmployeeBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public EmployeeBuilder withBossId(Long bossId) {
        this.bossId = bossId;
        return this;
    }

    @Override
    public EmployeeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EmployeeBuilder withPosition(String position) {
        this.position = position;
        return this;
    }

    @Override
    public EmployeeBuilder withBoss(Employee boss) {
        this.boss = boss;
        return this;
    }

    @Override
    public EmployeeBuilder withSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
        return this;
    }

    @Override
    public Employee build() {
        return new Employee(this.id,
                            this.bossId,
                            this.name,
                            this.position,
                            this.boss,
                            this.subordinates);
    }
}
