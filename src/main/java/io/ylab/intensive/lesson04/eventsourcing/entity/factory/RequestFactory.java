package io.ylab.intensive.lesson04.eventsourcing.entity.factory;

import io.ylab.intensive.lesson04.eventsourcing.entity.DeleteRequest;
import io.ylab.intensive.lesson04.eventsourcing.entity.PostRequest;
import io.ylab.intensive.lesson04.eventsourcing.entity.Request;
import io.ylab.intensive.lesson04.eventsourcing.entity.enums.Method;

import java.io.IOException;

public class RequestFactory {
    public Request getRequest(Method method) throws IOException {
        Request request;
        switch (method) {
            case POST:
                request = new PostRequest();
                break;
            case DELETE:
                request = new DeleteRequest();
                break;
            default:
                throw new IOException("Такого типа нет");
        }
        return request;
    }
}
