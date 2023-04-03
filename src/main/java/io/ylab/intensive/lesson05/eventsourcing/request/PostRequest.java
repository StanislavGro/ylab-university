package io.ylab.intensive.lesson05.eventsourcing.request;

import io.ylab.intensive.lesson05.eventsourcing.Person;

public class PostRequest extends Request {
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

    public void setPerson(Person person) {
        this.person = person;
    }
}
