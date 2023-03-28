package io.ylab.intensive.lesson04.eventsourcing.ex;

import io.ylab.intensive.lesson04.eventsourcing.request.RequestMethod;

import java.io.IOException;

public class RequestFactory {
    public Request getRequest(RequestMethod method) throws IOException {
        Request request;
        switch (method) {
            case POST:
                request = new PostRequest();
                break;
            case DELETE:
                request = new DeleteRequest();
                break;
            default:
                throw new IOException("Такого метода нет");
        }
        return request;
    }
}
