package io.ylab.intensive.lesson04.eventsourcing.request.body;

import io.ylab.intensive.lesson04.eventsourcing.Person;

public class PostBody implements RequestBody {
    private Person person;

    public PostBody(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
