package io.ylab.intensive.lesson05.eventsourcing.request;

public class DeleteRequest extends Request {
    private Long id;

    public DeleteRequest() {
        super(RequestMethod.DELETE);
    }

    public DeleteRequest(Long id) {
        super(RequestMethod.DELETE);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
