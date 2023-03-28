package io.ylab.intensive.lesson04.eventsourcing.request;

public class Request {
    private RequestMethod method;

    public Request(RequestMethod method) {
        this.method = method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }


    public RequestMethod getMethod() {
        return method;
    }

}
