package io.ylab.intensive.lesson04.eventsourcing.request;

import io.ylab.intensive.lesson04.eventsourcing.Person;

public class PostRequest extends Request{
    private Person person;

    public PostRequest() {
        super(RequestMethod.POST);
    }

    public PostRequest(Person person) {
        super(RequestMethod.POST);
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
