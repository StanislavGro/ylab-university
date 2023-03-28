package io.ylab.intensive.lesson04.eventsourcing.ex;

import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.request.RequestMethod;

public class PostRequest implements Request {
    private static final RequestMethod method = RequestMethod.POST;
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
