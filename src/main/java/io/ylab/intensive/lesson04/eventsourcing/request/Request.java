package io.ylab.intensive.lesson04.eventsourcing.request;

import io.ylab.intensive.lesson04.eventsourcing.request.body.RequestBody;

public class Request {
    private final RequestMethod method;
    private final RequestBody body;

    public Request(RequestMethod method, RequestBody body) {
        this.method = method;
        this.body = body;
    }

    public RequestBody getBody() {
        return body;
    }

    public RequestMethod getMethod() {
        return method;
    }
}
