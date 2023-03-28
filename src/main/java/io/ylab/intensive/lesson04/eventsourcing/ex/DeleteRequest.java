package io.ylab.intensive.lesson04.eventsourcing.ex;

import io.ylab.intensive.lesson04.eventsourcing.request.RequestMethod;

public class DeleteRequest implements Request {
    private static final RequestMethod method = RequestMethod.DELETE;
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
