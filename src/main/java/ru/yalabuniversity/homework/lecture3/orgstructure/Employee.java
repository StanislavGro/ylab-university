package ru.yalabuniversity.homework.lecture3.orgstructure;

import ru.yalabuniversity.homework.lecture3.orgstructure.builder.EmployeeBuilder;
import ru.yalabuniversity.homework.lecture3.orgstructure.builder.EmployeeBuilderImpl;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private Long id;
    private Long bossId;
    private String name;
    private String position;
    private Employee boss;
    private List<Employee> subordinates = new ArrayList<>();

    public Employee() {
    }

    public Employee(Long id, Long bossId, String name, String position, Employee boss, List<Employee> subordinates) {
        this.id = id;
        this.bossId = bossId;
        this.name = name;
        this.position = position;
        this.boss = boss;
        this.subordinates = subordinates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBossId() {
        return bossId;
    }

    public void setBossId(Long bossId) {
        this.bossId = bossId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Employee getBoss() {
        return boss;
    }

    public void setBoss(Employee boss) {
        this.boss = boss;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public static EmployeeBuilder builder() {
        return new EmployeeBuilderImpl();
    }
}
