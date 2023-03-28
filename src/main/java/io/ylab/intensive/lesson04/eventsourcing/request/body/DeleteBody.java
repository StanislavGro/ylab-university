package io.ylab.intensive.lesson04.eventsourcing.request.body;

public class DeleteBody implements RequestBody {
    private Long id;

    public DeleteBody(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
