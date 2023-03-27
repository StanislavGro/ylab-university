package io.ylab.intensive.lesson04.eventsourcing.entity;

import io.ylab.intensive.lesson04.eventsourcing.entity.enums.Method;

public class DeleteRequest implements Request {
    private static final Method method = Method.POST;
    private Long id;

    public static Method getMethod() {
        return method;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
