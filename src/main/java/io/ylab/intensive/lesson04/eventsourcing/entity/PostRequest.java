package io.ylab.intensive.lesson04.eventsourcing.entity;

import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.entity.enums.Method;

public class PostRequest implements Request{
    private static final Method method = Method.DELETE;
    private Person person;

    public static Method getMethod() {
        return method;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
